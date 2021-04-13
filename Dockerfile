FROM php:8.0.3-apache

LABEL maintainer="Marcel Jänicke <inpercima@gmail.com>"

RUN ln -snf /usr/share/zoneinfo/Europe/Berlin /etc/localtime \
    && echo "Europe/Berlin" > /etc/timezone \
    && apt-get update \
    && apt-get install wget -y

# preconfiguring apache
RUN a2enmod rewrite

# install pdo
RUN docker-php-ext-install pdo pdo_mysql

COPY ./client/dist /var/www/html/
