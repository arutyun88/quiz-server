# Многоэтапная сборка для оптимизации размера образа
FROM openjdk:17-jdk-alpine AS build

# Установка рабочей директории
WORKDIR /workspace/app

# Копирование файлов Gradle
COPY gradlew .
RUN chmod +x gradlew
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Загрузка зависимостей
RUN ./gradlew dependencies --no-daemon || return 0

# Копирование исходного кода
COPY src src

# Сборка jar
RUN ./gradlew clean bootJar --no-daemon
RUN mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*.jar)

# Финальный образ
FROM openjdk:17-jre-alpine

# Создание пользователя для запуска приложения
RUN addgroup -g 1001 -S spring && adduser -u 1001 -S spring -G spring

# Установка рабочей директории
WORKDIR /app

# Копирование jar файла из этапа сборки
ARG DEPENDENCY=/workspace/app/build/dependency
COPY --from=build --chown=spring:spring ${DEPENDENCY}/BOOT-INF/lib ./lib
COPY --from=build --chown=spring:spring ${DEPENDENCY}/META-INF ./META-INF
COPY --from=build --chown=spring:spring ${DEPENDENCY}/BOOT-INF/classes ./

# Переключение на пользователя spring
USER spring:spring

# Открытие порта
EXPOSE 8081

# Запуск приложения
ENTRYPOINT ["java","-cp","app:app/lib/*","com.arutyun.quiz_server.QuizServerApplication"]