# cryptocheck

[![MIT license](https://img.shields.io/badge/license-MIT-blue.svg)](./LICENSE.md)
[![dependencies Status](https://david-dm.org/inpercima/cryptocheck/status.svg)](https://david-dm.org/inpercima/cryptocheck)
[![devDependencies Status](https://david-dm.org/inpercima/cryptocheck/dev-status.svg)](https://david-dm.org/inpercima/cryptocheck?type=dev)

Determines profit and loss when buying cryptocurrencies at specific times.

This app is online under [cryptocheck.inpercima.net](http://cryptocheck.inpercima.net).

This project was generated with [swaaplate](https://github.com/inpercima/swaaplate) version 2.0.0-SNAPSHOT.

## Prerequisites

### Angular CLI

* `angular-cli 9.0.6` or higher

### Node, npm or yarn

* `node 12.16.1` or higher in combination with
  * `npm 6.13.4` or higher or
  * `yarn 1.22.0` or higher, used in this repository

## Dependency check

Some libraries could not be updated b/c of peer dependencies or knowing issues.

| library    | current version | wanted version | reason |
| ---------- | --------------- | -------------- | ------ |
| tslint     | 5.20.1          | 6.0.0          | "codelyzer@5.2.1" has incorrect peer dependency "tslint@^5.0.0" |
| typescript | 3.7.5           | 3.8.3          | " > @angular-devkit/build-angular@0.900.6" has incorrect peer dependency "typescript@>=3.6 < 3.8". |
| typescript | 3.7.5           | 3.8.3          | "@angular-devkit/build-angular > @ngtools/webpack@9.0.6" has incorrect peer dependency "typescript@>=3.6 < 3.8". |
| typescript | 3.7.5           | 3.8.3          | " > @angular/compiler-cli@9.0.6" has incorrect peer dependency "typescript@>=3.6 <3.8". |
| typescript | 3.7.5           | 3.8.3          | " > codelyzer@5.2.1" has incorrect peer dependency "tslint@^5.0.0". |

## Getting started

```bash
# clone project
git clone https://github.com/inpercima/cryptocheck
cd cryptocheck

# install tools and frontend dependencies
yarn
```

Create environment files for `devMode`, `mockMode` and `prodMode`.

```bash
cp src/environments/environment.ts src/environments/environment.dev.ts
cp src/environments/environment.ts src/environments/environment.mock.ts
cp src/environments/environment.ts src/environments/environment.prod.ts
```

**Note**: These files will not be under version control and listed in .gitignore.

## Usage

### Recommendation

It is recommanded to use a server to get full access of all angular.
You can do this for example with `yarn serve:mock`.
For the other options your app should run on a server which you like.

### Run in devMode

If you want to work with mock data, start the mock in a separate terminal, reachable on [http://localhost:3000/](http://localhost:3000/).

```bash
# mock, separate terminal
yarn run:mock
```

```bash
# build, reachable on http://localhost/app/path/to/dist/
yarn build:dev
# with mock
yarn build:mock

# build and starts a server, rebuild after changes, reachable on http://localhost:4200/
yarn serve:dev
# with mock
yarn serve:mock

# build, rebuild after changes, reachable on http://localhost/app/path/to/dist/
yarn watch:dev
# with mock
yarn watch:mock
```

### Package

```bash
# build in prodMode, compressed
yarn build:prod
```

### Tests

```bash
# test
ng test

# e2e
ng e2e
```

## Configuration

### General

All options have to been set in the environment files but some of them do not need to be changed.
All defaults refer to the development environment file (`environment.dev.ts`).
Change for prodMode the option `production` to `true` and for mockMode the option `api` to `http://localhost:3000/`.

### Table of contents

* [api](#api)
* [apiSuffix](#apiSuffix)
* [appname](#appname)
* [production](#production)
* [theme](#theme)

### `api`

Defines the URL to the backend.

* default: `./`
* type: `string`

### `apiSuffix`

Defines a suffix for the api to the backend.

* default: EMPTY
* type: `string`

### `appname`

Applicationwide title of the app, displayed in title and toolbar.

* default: `cryptocheck`
* type: `string`

### `production`

Defines whether the app is in production or not.

* default: `false`
* type: `boolean`
* values: `true`/`false`

### `theme`

Name of a build-in theme from angular-material or a custom light or dark theme.

* default: `indigo-pink`
* type: `string`
* values: `deeppurple-amber`/`indigo-pink`/`pink-bluegrey`/`purple-green`/`custom-light`/`custom-dark`

To create a custom light or dark theme just edit the colors and themes in `themes.scss`.
