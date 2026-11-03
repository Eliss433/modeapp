# Kleiderschrank App
## Project Structure
- frontend/: React application
- backend/: Spring Boot application
- infrastructure/: Docker config
- docs/: optional für Dokumentation (mal sehen ob wir das benutzen wollen)
## Getting Started
### Start DB

docker compose -f infrastructure/docker/docker-compose.yml up -d

### Start Backend 

cd backend
./mvnw spring-boot:run

### Start Frontend

cd frontend
npm install
npm run dev

