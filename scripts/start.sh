#!/bin/bash

# Цвета для вывода
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
GRAY='\033[0;37m'
NC='\033[0m'

echo -e "${GREEN}🚀 Запуск Quiz Server${NC}"

# Функция для определения статуса контейнеров
get_container_status() {
    if docker-compose ps -a --quiet | grep -q .; then
        if docker-compose ps --format "table {{.Status}}" | grep -q "Up"; then
            echo "running"
        else
            echo "stopped"
        fi
    else
        echo "none"
    fi
}

# Функция для выбора профиля
choose_profile() {
    echo -e "${BLUE}Выберите режим запуска:${NC}"
    echo -e "  ${YELLOW}1)${NC} Продакшн режим (требует .env файл)"
    echo -e "  ${YELLOW}2)${NC} Режим разработки (использует .env.dev для контейнеров)"
    read -p "Введите номер (1-2): " mode_choice

    # Проверка валидности выбора режима
    if ! [[ "$mode_choice" =~ ^[1-2]$ ]]; then
        echo -e "${RED}❌ Неверный выбор. Введите число 1 или 2${NC}"
        exit 1
    fi

    # Проверка файлов в зависимости от выбранного режима
    if [ "$mode_choice" = "1" ]; then
        # Продакшн режим - проверяем .env
        if [ ! -f .env ]; then
            echo -e "${RED}❌ Файл .env не найден${NC}"
            echo -e "${YELLOW}📋 Создайте файл .env на основе .env.dev${NC}"
echo -e "${BLUE}💡 Команда: cp .env.dev .env${NC}"
            echo -e "${RED}❗ Отредактируйте .env файл и запустите скрипт снова${NC}"
            exit 1
        fi
        echo -e "${GREEN}✅ Продакшн режим: .env файл найден${NC}"
        check_required_variables
    else
        # Dev режим - проверяем .env.dev
if [ ! -f .env.dev ]; then
echo -e "${RED}❌ Файл .env.dev не найден${NC}"
echo -e "${RED}❗ Файл .env.dev необходим для запуска контейнеров в dev режиме${NC}"
exit 1
fi
echo -e "${GREEN}✅ Режим разработки: .env.dev файл найден${NC}"
        check_required_variables
    fi
}

# Определение статуса контейнеров
container_status=$(get_container_status)

# Динамическое меню в зависимости от статуса
echo -e "\n${BLUE}Выберите действие:${NC}"
echo -e "${GRAY}Текущий статус контейнеров: ${NC}"

case $container_status in
    "running")
        echo -e "${GREEN}🟢 Контейнеры запущены${NC}"
        echo -e "  ${YELLOW}1)${NC} Только база данных (для разработки в IDE)"
        echo -e "  ${YELLOW}2)${NC} Полный стек в Docker (перезапуск существующих)"
        echo -e "  ${YELLOW}3)${NC} Перезапустить существующие контейнеры"
        echo -e "  ${YELLOW}4)${NC} Создать контейнеры заново"
        echo -e "  ${YELLOW}5)${NC} Остановить контейнеры (без удаления)"
        echo -e "  ${YELLOW}6)${NC} Остановить и удалить все"
        max_choice=6
        ;;
    "stopped")
        echo -e "${YELLOW}🟡 Контейнеры остановлены${NC}"
        echo -e "  ${YELLOW}1)${NC} Только база данных"
        echo -e "  ${YELLOW}2)${NC} Полный стек в Docker"
        echo -e "  ${YELLOW}3)${NC} Перезапустить существующие контейнеры"
        echo -e "  ${YELLOW}4)${NC} Создать контейнеры заново"
        echo -e "  ${YELLOW}5)${NC} Остановить и удалить все"
        max_choice=5
        ;;
    "none")
        echo -e "${BLUE}🔵 Контейнеры не существуют${NC}"
        echo -e "  ${YELLOW}1)${NC} Только база данных (рекомендуется для разработки)"
        echo -e "  ${YELLOW}2)${NC} Полный стек в Docker"
        max_choice=2
        ;;
esac

read -p "Введите номер (1-$max_choice): " choice

# Проверка валидности выбора
if ! [[ "$choice" =~ ^[1-$max_choice]$ ]]; then
    echo -e "${RED}❌ Неверный выбор. Введите число от 1 до $max_choice${NC}"
    exit 1
fi

# Переменная для определения, нужен ли выбор профиля
need_profile_choice=false

# Определяем, когда нужен выбор профиля
case $choice in
    2|4) # Полный стек или создание заново
        need_profile_choice=true
        ;;
    3) # Перезапуск - только если контейнеры были запущены
        if [ "$container_status" = "running" ]; then
            need_profile_choice=true
        fi
        ;;
esac

# Выбираем профиль только когда это необходимо
if [ "$need_profile_choice" = true ]; then
    echo ""
    choose_profile
else
    # Для простых операций (только БД, остановка) используем dev режим по умолчанию
    mode_choice="2"
fi

# Функция для получения переменной SERVER_PORT из соответствующего файла
get_server_port() {
    if [ "$mode_choice" = "1" ]; then
        # Продакшн - читаем из .env
        if [ -f .env ]; then
            port=$(grep "^SERVER_PORT=" .env | cut -d'=' -f2 | tr -d '"' | tr -d "'")
            echo "${port:-8081}"
        else
            echo "8081"
        fi
    else
        # Dev - читаем из .env.dev
if [ -f .env.dev ]; then
port=$(grep "^SERVER_PORT=" .env.dev | cut -d'=' -f2 | tr -d '"' | tr -d "'")
            echo "${port:-8081}"
        else
            echo "8081"
        fi
    fi
}

# Функция для получения POSTGRES_PORT из соответствующего файла
get_postgres_port() {
    if [ "$mode_choice" = "1" ]; then
        # Продакшн - читаем из .env
        if [ -f .env ]; then
            port=$(grep "^POSTGRES_PORT=" .env | cut -d'=' -f2 | tr -d '"' | tr -d "'")
            echo "${port:-5432}"
        else
            echo "5432"
        fi
    else
        # Dev - читаем из .env.dev
if [ -f .env.dev ]; then
port=$(grep "^POSTGRES_PORT=" .env.dev | cut -d'=' -f2 | tr -d '"' | tr -d "'")
            echo "${port:-5432}"
        else
            echo "5432"
        fi
    fi
}

# Функция для определения правильного env файла
get_env_file_flag() {
    if [ "$mode_choice" = "1" ]; then
        echo "--env-file .env"
    else
        echo "--env-file .env.dev"
    fi
}

# Функция для проверки обязательных переменных
check_required_variables() {
    local env_file=""
    if [ "$mode_choice" = "1" ]; then
        env_file=".env"
    else
        env_file=".env.dev"
    fi

    echo -e "${BLUE}🔍 Проверка переменных в $env_file...${NC}"

    # Список обязательных переменных
    local required_vars=("JWT_SECRET_KEY" "POSTGRES_PASSWORD" "POSTGRES_URL")
    local missing_vars=()

    for var in "${required_vars[@]}"; do
        if ! grep -q "^${var}=" "$env_file" 2>/dev/null || [ -z "$(grep "^${var}=" "$env_file" | cut -d'=' -f2)" ]; then
            missing_vars+=("$var")
        fi
    done

    if [ ${#missing_vars[@]} -gt 0 ]; then
        echo -e "${YELLOW}⚠️  Внимание! Не заданы обязательные переменные:${NC}"
        for var in "${missing_vars[@]}"; do
            echo -e "  ${RED}❌ $var${NC}"
        done
        echo -e "${BLUE}💡 Отредактируйте файл $env_file${NC}"
    else
        echo -e "${GREEN}✅ Все обязательные переменные заданы${NC}"
    fi
}

# Выполнение действий
case $choice in
    1) # Только база данных
        env_flag=$(get_env_file_flag)
        if [ "$container_status" = "running" ]; then
            echo -e "${GREEN}🔄 Перезапуск PostgreSQL...${NC}"
            docker-compose $env_flag restart postgres
        else
            echo -e "${GREEN}🐘 Запуск PostgreSQL...${NC}"
            docker-compose $env_flag up -d postgres
        fi
        echo -e "${GREEN}✅ PostgreSQL готов для разработки${NC}"
        postgres_port=$(get_postgres_port)
        echo -e "${BLUE}💡 PostgreSQL доступен на localhost:${postgres_port}${NC}"
        echo -e "${BLUE}💡 Теперь запустите приложение с профилем dev:${NC}"
        echo -e "${GRAY}   ./gradlew bootRun --args='--spring.profiles.active=dev'${NC}"
        echo -e "${GRAY}   Или в IDE с VM options: -Dspring.profiles.active=dev${NC}"
        ;;
    2) # Полный стек
        env_flag=$(get_env_file_flag)
        server_port=$(get_server_port)
        server_port=${server_port:-8081}  # По умолчанию 8081 если не найден

        if [ "$mode_choice" = "1" ]; then
            echo -e "${GREEN}🐳 Запуск полного стека (продакшн)...${NC}"
            docker-compose $env_flag up --build -d
        else
            echo -e "${GREEN}🐳 Запуск полного стека (разработка)...${NC}"
            docker-compose $env_flag up -d
        fi
        sleep 5
        echo -e "${GREEN}✅ Полный стек запущен${NC}"
        echo -e "${BLUE}🌐 Приложение доступно: http://localhost:${server_port}${NC}"
        ;;
    3) # Перезапуск существующих
        env_flag=$(get_env_file_flag)
        server_port=$(get_server_port)
        server_port=${server_port:-8081}

        if [ "$container_status" = "running" ]; then
            if [ "$mode_choice" = "1" ]; then
                echo -e "${GREEN}🔄 Перезапуск контейнеров (продакшн)...${NC}"
                docker-compose $env_flag up --build -d
            else
                echo -e "${GREEN}🔄 Перезапуск контейнеров (разработка)...${NC}"
                docker-compose $env_flag up -d
            fi
        else
            echo -e "${GREEN}🔄 Запуск остановленных контейнеров...${NC}"
            docker-compose $env_flag up -d
        fi
        sleep 5
        echo -e "${GREEN}✅ Контейнеры перезапущены${NC}"
        echo -e "${BLUE}🌐 Приложение доступно: http://localhost:${server_port}${NC}"
        ;;
    4) # Создать заново
        env_flag=$(get_env_file_flag)
        server_port=$(get_server_port)
        server_port=${server_port:-8081}

        if [ "$mode_choice" = "1" ]; then
            echo -e "${GREEN}🆕 Создание контейнеров заново (продакшн)...${NC}"
            docker-compose $env_flag up --build --force-recreate -d
        else
            echo -e "${GREEN}🆕 Создание контейнеров заново (разработка)...${NC}"
            docker-compose $env_flag up --build --force-recreate -d
        fi
        sleep 5
        echo -e "${GREEN}✅ Контейнеры созданы заново${NC}"
        echo -e "${BLUE}🌐 Приложение доступно: http://localhost:${server_port}${NC}"
        ;;
    5) # Остановить
        if [ "$container_status" = "stopped" ]; then
            echo -e "${YELLOW}🛑 Остановка и удаление всех сервисов...${NC}"
            docker-compose down
            echo -e "${GREEN}✅ Все сервисы остановлены и удалены${NC}"
        else
            echo -e "${YELLOW}⏸️  Остановка контейнеров (без удаления)...${NC}"
            docker-compose stop
            echo -e "${GREEN}✅ Контейнеры остановлены${NC}"
            echo -e "${BLUE}💡 Контейнеры сохранены и могут быть перезапущены${NC}"
        fi
        ;;
    6) # Остановить и удалить (только для running статуса)
        echo -e "${YELLOW}🛑 Остановка и удаление всех сервисов...${NC}"
        docker-compose down
        echo -e "${GREEN}✅ Все сервисы остановлены и удалены${NC}"
        ;;
    *)
        echo -e "${RED}❌ Неверный выбор${NC}"
        exit 1
        ;;
esac

# Показать статус (кроме случаев остановки/удаления)
if [ "$choice" != "5" ] && [ "$choice" != "6" ]; then
    echo -e "\n${BLUE}📊 Статус сервисов:${NC}"
    docker-compose ps

    # Дополнительная диагностика для полного стека
    if [ "$choice" = "2" ] || [ "$choice" = "3" ] || [ "$choice" = "4" ]; then
        echo -e "\n${BLUE}🔍 Диагностика подключения:${NC}"
        sleep 3

        # Проверяем доступность приложения
        if curl -s http://localhost:${server_port}/actuator/health > /dev/null 2>&1; then
            echo -e "${GREEN}✅ Приложение отвечает на http://localhost:${server_port}${NC}"
        else
            echo -e "${YELLOW}⚠️  Приложение не отвечает на порту ${server_port}${NC}"
            echo -e "${BLUE}💡 Проверьте логи: docker-compose logs app${NC}"
        fi
    fi
fi

# Дополнительная информация
echo -e "\n${GREEN}🎯 Полезные команды:${NC}"
echo -e "  ${GRAY}Логи приложения:${NC} docker-compose logs -f app"
echo -e "  ${GRAY}Логи PostgreSQL:${NC} docker-compose logs -f postgres"
echo -e "  ${GRAY}Войти в контейнер:${NC} docker-compose exec app bash"
echo -e "  ${GRAY}Проверить здоровье:${NC} curl http://localhost:${server_port}/actuator/health"
echo -e "  ${GRAY}Остановить:${NC} docker-compose stop"
echo -e "  ${GRAY}Удалить:${NC} docker-compose down"

# Дополнительная информация для troubleshooting
if [ "$choice" = "2" ] || [ "$choice" = "3" ] || [ "$choice" = "4" ]; then
    echo -e "\n${YELLOW}🚨 Если приложение недоступно:${NC}"
    echo -e "  1. ${GRAY}Проверьте логи:${NC} docker-compose logs app"
    echo -e "  2. ${GRAY}Убедитесь в правильности .env${NC}"
    echo -e "  3. ${GRAY}Проверьте server.address=0.0.0.0 в application.yml${NC}"
    echo -e "  4. ${GRAY}Убедитесь, что порт ${server_port} свободен:${NC} netstat -tlnp | grep ${server_port}"
fi