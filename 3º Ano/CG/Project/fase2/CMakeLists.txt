cmake_minimum_required(VERSION 3.5)

# Project Name
PROJECT(fase1)
set(CMAKE_CXX_STANDARD 20)
set_property(GLOBAL PROPERTY USE_FOLDERS ON)

# Define os executáveis a serem construídos neste projecto
add_executable(engine.exe ./src/engine/engine.cpp)
add_executable(generator.exe ./src/generator/generator.cpp ./src/generator/geometry.cpp)

# Biblioteca das figuras e dos pontos
add_library(
    utils_lib
    ./src/utils/ponto.cpp
    ./src/utils/ponto.hpp
    ./src/utils/figura.cpp
    ./src/utils/figura.hpp
    ./src/utils/list.cpp
    ./src/utils/list.hpp
)

# Biblioteca de parsing de XML
add_library(
    tinyXML
    ./src/tinyXML/tinyxml2.cpp
    ./src/tinyXML/tinyxml2.h
)

# Biblioteca de configuração do engine.
add_library(
    parser_lib
    ./src/utils/parser.cpp
    ./src/utils/parser.hpp
)

target_link_libraries(parser_lib tinyXML)

# Associa a biblioteca das figuras e dos pontos aos executáveis
# target_link_libraries(generator.exe utils_lib)
target_link_libraries(engine.exe tinyXML utils_lib parser_lib)

find_package(OpenGL REQUIRED)
include_directories(${OpenGL_INCLUDE_DIRS})
link_directories(${OpenGL_LIBRARY_DIRS})
add_definitions(${OpenGL_DEFINITIONS})

if(NOT OPENGL_FOUND)
    message(ERROR " OPENGL not found!")
endif(NOT OPENGL_FOUND)

if(WIN32)
    # Windows specific configurations
    message(STATUS "Configuring for Windows")

    set(TOOLKITS_FOLDER "" CACHE PATH "Path to Toolkits folder")

    if (NOT EXISTS "${TOOLKITS_FOLDER}/glut/GL/glut.h" OR NOT EXISTS "${TOOLKITS_FOLDER}/glut/glut32.lib")
        message(ERROR ": GLUT not found")
    endif (NOT EXISTS "${TOOLKITS_FOLDER}/glut/GL/glut.h" OR NOT EXISTS "${TOOLKITS_FOLDER}/glut/glut32.lib")

    include_directories(${TOOLKITS_FOLDER}/glut)
    target_link_libraries(generator.exe ${OPENGL_LIBRARIES} ${TOOLKITS_FOLDER}/glut/glut32.lib)
    target_link_libraries(engine.exe ${OPENGL_LIBRARIES} ${TOOLKITS_FOLDER}/glut/glut32.lib)

    if (EXISTS "${TOOLKITS_FOLDER}/glut/glut32.dll")
        file(COPY ${TOOLKITS_FOLDER}/glut/glut32.dll DESTINATION ${CMAKE_BINARY_DIR})
    endif (EXISTS "${TOOLKITS_FOLDER}/glut/glut32.dll")
else()
    # Linux and macOS specific configurations
    message(STATUS "Configuring for Linux or macOS")

    set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Wall -Wextra")
    find_package(GLUT REQUIRED)
    include_directories(${GLUT_INCLUDE_DIR})
    link_directories(${GLUT_LIBRARY_DIRS})
    add_definitions(${GLUT_DEFINITIONS})

    target_link_libraries(generator.exe ${OPENGL_LIBRARIES} ${GLUT_LIBRARIES})
    target_link_libraries(engine.exe ${OPENGL_LIBRARIES} ${GLUT_LIBRARIES})

    if(NOT GLUT_FOUND)
        message(ERROR ": GLUT not found!")
    endif(NOT GLUT_FOUND)
endif(WIN32)
