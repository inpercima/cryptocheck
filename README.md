# cryptocheck

[![MIT license](https://img.shields.io/badge/license-MIT-blue.svg)](./LICENSE.md)

Simple additional app for bitanda and other exchanges or services to see further details such as profit/loss and tax.

This project was generated with [swaaplate](https://github.com/inpercima/swaaplate) version 2.3.1.

## Prerequisites

### Angular CLI

* `angular-cli 13.3.2` or higher

### Java

* `jdk 17` or higher

### Node, npm or yarn

* `node 16.13.0` or higher in combination with
  * `npm 8.1.0` or higher or
  * `yarn 1.22.17` or higher, used in this repository

### Bitpanda registration and api-key

This app assumes that an account has already been created and data has been exchanged.
Therefore use this account to generate an api-key with it.
[Here you get the information how it works.](https://support.bitpanda.com/hc/en-us/articles/360000727459-Bitpanda-API-Key-and-price-ticker-API)

## Getting started

```bash
# clone project
git clone https://github.com/inpercima/cryptocheck
cd cryptocheck
```

## Usage

### General

This repository includes two backend possibilities.
You can run this program with php or java.
Per default java is selected and recommended.

At the moment it is not possible to work with php b/c of breaking changes made with java.

### Modules

For the client check [cryptocheck - client](./client).

For the java server check [cryptocheck - server](./server).

For the php api check [cryptocheck - api](./api).
