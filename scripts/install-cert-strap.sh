#!/bin/bash

version=1.3.0
unameOut="$(uname -s)"
case "${unameOut}" in
    Linux*)     machine=linux;;
    Darwin*)    machine=darwin;;
    *)          exit -1
esac
curl -L https://github.com/square/certstrap/releases/download/v${version}/certstrap-${machine}-amd64 > ../bin/certstrap
chmod +x ../bin/certstrap
