# Weather API Wrapper Service

A Spring Boot-based weather API service that fetches and returns weather data from third-party APIs with Redis caching capabilities. This project is part of the [roadmap.sh](https://roadmap.sh/projects/weather-api-wrapper-service) learning path.

## Technology Stack

- Java 24
- Spring Boot 3.5.0
- Spring Data Redis
- Spring Web
- Lombok
- Java-dotenv for environment variable management

## Features

- 🌤️ Weather data retrieval from third-party APIs
- 🔄 Redis-based caching implementation
- ⚡ Automatic cache expiration
- 🔒 Environment variable management using java-dotenv
- 🛡️ Spring Security (planned)
- ⚠️ Comprehensive error handling

## Prerequisites

- Java 24 or later
- Gradle
- Redis server
- API key from a weather service provider (e.g., Visual Crossing)

## Setup

1. Clone the repository:

```bash
git clone [your-repo-url]
cd WeatherApi
```

2. Create a `.env` file in the root directory with the following variables:

```env
WEATHER_API_KEY=your_api_key_here
REDIS_HOST=localhost
REDIS_PORT=6379
```

3. Build the project:

```bash
./gradlew build
```

4. Run the application:

```bash
./gradlew bootRun
```

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── io/github/dhar135/
│   │       ├── controllers/
│   │       ├── services/
│   │       ├── models/
│   │       └── config/
│   └── resources/
│       └── application.properties
└── test/
    └── java/
```

## Development

This project uses:

- Spring Boot DevTools for enhanced development experience
- Lombok for reducing boilerplate code
- Spring Data Redis for caching implementation
- Spring Web for REST API endpoints

## Testing

Run the tests using:

```bash
./gradlew test
```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- [Visual Crossing Weather API](https://www.visualcrossing.com/) for providing free weather data
- [roadmap.sh](https://roadmap.sh) for the project idea and guidance
- Spring Boot team for the excellent framework
