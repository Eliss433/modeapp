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
#### Linux
cd backend
./mvnw spring-boot:run

#### Windows

cd backend
.\mvnw.cmd spring-boot:run

### Start Frontend

#### Linux
cd frontend
npm install
npm run dev

#### Windows

cd frontend
npm ci
npm run dev
