cmake_minimum_required(VERSION 3.15)
project(RastrosProject C)

set(CMAKE_C_STANDARD 99)

add_executable(RastrosProject main.c interface.c interface.h camadadedados.c camadadedados.h logic.c logic.h lista.c lista.h)
