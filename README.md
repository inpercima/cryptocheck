# cryptocheck

[![MIT license](https://img.shields.io/badge/license-MIT-blue.svg)](./LICENSE.md)

Determines profit and loss when buying cryptocurrencies at specific times.

This project was generated with [swaaplate](https://github.com/inpercima/swaaplate) version 2.0.1.

## Prerequisites

### Angular CLI

* `angular-cli 10.1.6` or higher

### Apache and php

* `Apache 2.4` or higher
* `php 7.3` or higher

### Node, npm or yarn

* `node 12.16.1` or higher in combination with
  * `npm 6.13.4` or higher or
  * `yarn 1.22.5` or higher, used in this repository

## Dependency check

Some libraries could not be updated b/c of peer dependencies or knowing issues.

| library    | current version | wanted version | reason |
| ---------- | --------------- | -------------- | ------ |
| zone.js | 0.10.3 | 0.11.1 | "@angular/core@10.1.3" has incorrect peer dependency "zone.js@~0.10.3" |
| copy-webpack-plugin | 4.6.0 | 6.1.1 | "copy-webpack-plugin@6.1.1" has unmet peer dependency "webpack@^4.0.0 || ^5.0.0" |

## Getting started

```bash
# clone project
git clone https://github.com/inpercima/cryptocheck
cd cryptocheck
```

## Usage

### Modules

For the client check [cryptocheck - client](./client).

For the server check [cryptocheck - server](./server).
