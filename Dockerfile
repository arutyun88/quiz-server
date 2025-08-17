# Многоэтапная сборка для оптимизации размера образа
FROM eclipse-temurin:17-jdk AS build

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
FROM eclipse-temurin:17-jre

# Создание пользователя для запуска приложения
RUN groupadd -g 1001 spring && useradd -u 1001 -g spring -s /bin/bash spring

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
ENTRYPOINT ["java","-cp",".:./lib/*","com.arutyun.quiz_server.QuizServerApplication"]