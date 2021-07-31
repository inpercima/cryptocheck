# cryptocheck - server

## Getting started

```bash
# all commands used in ./server
cd server
```

Create environment files for `devMode` and `prodMode`.

```bash
cp src/main/resources/application.yml src/main/resources/application-dev.yml
cp src/main/resources/application.yml src/main/resources/application-prod.yml
```

**Note**: These files will not be under version control but listed in .gitignore.

## Usage

### Run in devMode

```bash
# short
./mvnw

# long
./mvnw spring-boot:run -Pdev
```

### Run in prodMode

```bash
# short
./mvnw -Pprod

# long
./mvnw spring-boot:run -Pprod
```

### Package and run in prodMode

```bash
# package
./mvnw clean package

# package without tests
./mvnw clean package -DskipTests

# place the `application-prod.yml` aside the cryptocheck-1.0.0-SNAPSHOT.jar and run the jar
cp src/main/resources/application-prod.yml target/application-prod.yml
cd target
java -jar cryptocheck-1.0.0-SNAPSHOT.jar --spring.profiles.active=prod
```
