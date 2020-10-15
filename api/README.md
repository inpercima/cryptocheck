# cryptocheck - api

## Getting started

```bash
# all commands used in ./api
cd api
```

Create config file.

```bash
cp src/main/config.default.php src/main/config.php
```

**Note**: This file will not be under version control but listed in .gitignore.

## Configuration

### Table of contents

* [API_KEY](#APIKEY)
* [DB_HOST](#DBHOST)
* [DB_NAME](#DBNAME)
* [DB_PASS](#DBPASS)
* [DB_USER](#DBUSER)

### `API_KEY`

Defines the api key for bitpanda.

* default: EMPTY
* type: `string`

### `DB_HOST`

Defines the host of the using mysql db.

* default: EMPTY
* type: `string`

### `DB_NAME`

Defines the name of the using mysql db.

* default: EMPTY
* type: `string`

### `DB_PASS`

Defines the password of the using mysql db.

* default: EMPTY
* type: `string`

### `DB_USER`

Defines the user of the using mysql db.

* default: EMPTY
* type: `string`
