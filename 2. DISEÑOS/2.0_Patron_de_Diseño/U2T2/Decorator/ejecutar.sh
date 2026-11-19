#!/usr/bin/env sh
set -eu
cd "$(dirname "$0")"
mkdir -p out
javac -encoding UTF-8 --release 17 -d out @sources.txt
java -cp out presentacion.Main
