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

# Проверка .env файла
if [ ! -f .env ]; then
    echo -e "${YELLOW}⚠️  Файл .env не найден${NC}"
    if [ -f env.example ]; then
        echo -e "${BLUE}📋 Копирую env.example в .env${NC}"
        cp env.example .env
        echo -e "${RED}❗ Отредактируйте .env файл и запустите скрипт снова${NC}"
        exit 1
    else
        echo -e "${RED}❌ Файл env.example не найден${NC}"
        exit 1
    fi
fi

# Определение статуса контейнеров
container_status=$(get_container_status)

# Динамическое меню в зависимости от статуса
echo -e "${BLUE}Выберите режим запуска:${NC}"
echo -e "${GRAY}Текущий статус: ${NC}"

case $container_status in
    "running")
        echo -e "${GREEN}🟢 Контейнеры запущены${NC}"
        echo -e "  ${YELLOW}1)${NC} Только база данных (для разработки в IDE)"
        echo -e "  ${YELLOW}2)${NC} Полный стек в Docker (перезапуск существующих)"
        echo -e "  ${YELLOW}4)${NC} Создать контейнеры заново"
        echo -e "  ${YELLOW}5)${NC} Остановить контейнеры (без удаления)"
        echo -e "  ${YELLOW}6)${NC} Остановить и удалить все"
        max_choice=6
        ;;
    "stopped")
        echo -e "${YELLOW}🟡 Контейнеры остановлены${NC}"
        echo -e "  ${YELLOW}1)${NC} Только база данных (для разработки в IDE)"
        echo -e "  ${YELLOW}2)${NC} Полный стек в Docker (перезапуск существующих)"
        echo -e "  ${YELLOW}3)${NC} Перезапустить существующие контейнеры"
        echo -e "  ${YELLOW}6)${NC} Остановить и удалить все"
        max_choice=6
        ;;
    "none")
        echo -e "${BLUE}🔵 Контейнеры не существуют${NC}"
        echo -e "  ${YELLOW}1)${NC} Только база данных (для разработки в IDE)"
        echo -e "  ${YELLOW}2)${NC} Полный стек в Docker (перезапуск существующих)"
        max_choice=2
        ;;
esac

read -p "Введите номер (1-$max_choice): " choice

# Проверка валидности выбора
if ! [[ "$choice" =~ ^[1-$max_choice]$ ]]; then
    echo -e "${RED}❌ Неверный выбор. Введите число от 1 до $max_choice${NC}"
    exit 1
fi

case $choice in
    1)
        echo -e "${GREEN}🐘 Запуск только PostgreSQL...${NC}"
        docker-compose up -d postgres
        echo -e "${GREEN}✅ PostgreSQL запущен на localhost:5432${NC}"
        echo -e "${BLUE}💡 Теперь запустите приложение из IDE${NC}"
        ;;
    2)
        echo -e "${GREEN}🐳 Запуск полного стека...${NC}"
        docker-compose up --build -d
        sleep 5
        echo -e "${GREEN}✅ Полный стек запущен${NC}"
        echo -e "${BLUE}🌐 Приложение доступно: http://localhost:8081${NC}"
        ;;
    3)
        echo -e "${GREEN}🔄 Перезапуск существующих контейнеров...${NC}"
        docker-compose up -d
        sleep 5
        echo -e "${GREEN}✅ Контейнеры перезапущены${NC}"
        echo -e "${BLUE}🌐 Приложение доступно: http://localhost:8081${NC}"
        ;;
    4)
        echo -e "${GREEN}🆕 Создание контейнеров заново...${NC}"
        docker-compose up --build --force-recreate -d
        sleep 5
        echo -e "${GREEN}✅ Контейнеры созданы заново${NC}"
        echo -e "${BLUE}🌐 Приложение доступно: http://localhost:8081${NC}"
        ;;
    5)
        echo -e "${YELLOW}⏸️  Остановка контейнеров (без удаления)...${NC}"
        docker-compose stop
        echo -e "${GREEN}✅ Контейнеры остановлены${NC}"
        echo -e "${BLUE}💡 Контейнеры сохранены и могут быть перезапущены${NC}"
        ;;
    6)
        echo -e "${YELLOW}🛑 Остановка и удаление всех сервисов...${NC}"
        docker-compose down
        echo -e "${GREEN}✅ Все сервисы остановлены и удалены${NC}"
        ;;
    *)
        echo -e "${RED}❌ Неверный выбор${NC}"
        exit 1
        ;;
esac

# Показать статус
if [ "$choice" != "5" ] && [ "$choice" != "6" ]; then
    echo -e "\n${BLUE}📊 Статус сервисов:${NC}"
    docker-compose ps
fi