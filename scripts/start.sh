#!/bin/bash

# Цвета для вывода
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m'

echo -e "${GREEN}🚀 Запуск Quiz Server${NC}"

# Проверка .env файла
if [ ! -f .env ]; then
    echo -e "${YELLOW}⚠️  Файл .env не найден${NC}"
    if [ -f .env.example ]; then
        echo -e "${BLUE}📋 Копирую .env.example в .env${NC}"
        cp .env.example .env
        echo -e "${RED}❗ Отредактируйте .env файл и запустите скрипт снова${NC}"
        exit 1
    else
        echo -e "${RED}❌ Файл .env.example не найден${NC}"
        exit 1
    fi
fi

# Выбор режима запуска
echo -e "${BLUE}Выберите режим запуска:${NC}"
echo -e "  ${YELLOW}1)${NC} Только база данных (для разработки в IDE)"
echo -e "  ${YELLOW}2)${NC} Полный стек в Docker"
echo -e "  ${YELLOW}3)${NC} Остановить все"

read -p "Введите номер (1-3): " choice

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
        echo -e "${YELLOW}🛑 Остановка всех сервисов...${NC}"
        docker-compose down
        echo -e "${GREEN}✅ Все сервисы остановлены${NC}"
        ;;
    *)
        echo -e "${RED}❌ Неверный выбор${NC}"
        exit 1
        ;;
esac

# Показать статус
if [ "$choice" != "3" ]; then
    echo -e "\n${BLUE}📊 Статус сервисов:${NC}"
    docker-compose ps
fi