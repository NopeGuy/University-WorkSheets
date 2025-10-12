cmake_minimum_required(VERSION 3.5)

# Project Name
PROJECT(fase4)
set(CMAKE_CXX_STANDARD 20)
set_property(GLOBAL PROPERTY USE_FOLDERS ON)

# Define the executables to be built in this project
add_executable(engine.exe ./src/engine/engine.cpp)
add_executable(generator.exe ./src/generator/generator.cpp ./src/generator/geometry.cpp)

# Library for figures and points
add_library(
    utils_lib
    ./src/utils/ponto.cpp
    ./src/utils/ponto.hpp
    ./src/utils/figura.cpp
    ./src/utils/figura.hpp
    ./src/utils/list.cpp
    ./src/utils/list.hpp
    ./src/utils/curve.cpp
    ./src/utils/curve.hpp
)

# XML parsing library
add_library(
    tinyXML
    ./src/tinyXML/tinyxml2.cpp
    ./src/tinyXML/tinyxml2.h
)

# Engine configuration library
add_library(
    parser_lib
    ./src/utils/parser.cpp
    ./src/utils/parser.hpp
)

target_link_libraries(parser_lib tinyXML)

# Link libraries to executables
target_link_libraries(generator.exe utils_lib)
target_link_libraries(engine.exe tinyXML utils_lib parser_lib)

# OpenGL configuration
find_package(OpenGL REQUIRED)
include_directories(${OPENGL_INCLUDE_DIRS})
link_directories(${OPENGL_LIBRARY_DIRS})
add_definitions(${OPENGL_DEFINITIONS})

if(NOT OPENGL_FOUND)
    message(ERROR "OPENGL not found!")
endif(NOT OPENGL_FOUND)

if(WIN32)
    # Windows specific configurations
    message(STATUS "Configuring for Windows")

    set(TOOLKITS_FOLDER "" CACHE PATH "Path to Toolkits folder")

    if (NOT EXISTS "${TOOLKITS_FOLDER}/glut/GL/glut.h" OR NOT EXISTS "${TOOLKITS_FOLDER}/glut/glut32.lib")
        message(ERROR ": GLUT not found")
    endif()

    if (NOT EXISTS "${TOOLKITS_FOLDER}/glew/GL/glew.h" OR NOT EXISTS "${TOOLKITS_FOLDER}/glew/glew32.lib")
        message(ERROR ": GLEW not found")
    endif()

    if (NOT EXISTS "${TOOLKITS_FOLDER}/devil/IL/il.h" OR NOT EXISTS "${TOOLKITS_FOLDER}/devil/devIL.lib")
        message(ERROR ": DevIL not found")
    endif()

    include_directories(${TOOLKITS_FOLDER}/glut ${TOOLKITS_FOLDER}/glew ${TOOLKITS_FOLDER}/devil)
    target_link_libraries(generator.exe ${OPENGL_LIBRARIES} ${TOOLKITS_FOLDER}/glut/glut32.lib ${TOOLKITS_FOLDER}/glew/glew32.lib ${TOOLKITS_FOLDER}/devil/devIL.lib)
    target_link_libraries(engine.exe ${OPENGL_LIBRARIES} ${TOOLKITS_FOLDER}/glut/glut32.lib ${TOOLKITS_FOLDER}/glew/glew32.lib ${TOOLKITS_FOLDER}/devil/devIL.lib)

    if (EXISTS "${TOOLKITS_FOLDER}/glut/glut32.dll" AND EXISTS "${TOOLKITS_FOLDER}/glew/glew32.dll" AND EXISTS "${TOOLKITS_FOLDER}/devil/devil.dll")
        file(COPY ${TOOLKITS_FOLDER}/glut/glut32.dll DESTINATION ${CMAKE_BINARY_DIR})
        file(COPY ${TOOLKITS_FOLDER}/glew/glew32.dll DESTINATION ${CMAKE_BINARY_DIR})
        file(COPY ${TOOLKITS_FOLDER}/devil/devil.dll DESTINATION ${CMAKE_BINARY_DIR})
    endif()

elseif(NOT APPLE)
    # Linux specific configurations
    message(STATUS "Configuring for Linux")

    set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Wall -Wextra")
    find_package(GLUT REQUIRED)
    find_package(GLEW REQUIRED)
    find_package(DevIL REQUIRED)
    include_directories(${GLUT_INCLUDE_DIRS} ${GLEW_INCLUDE_DIRS} ${IL_INCLUDE_DIR})
    link_directories(${GLUT_LIBRARY_DIRS} ${GLEW_LIBRARY_DIRS} ${IL_LIBRARY_DIRS})
    add_definitions(${GLUT_DEFINITIONS} ${GLEW_DEFINITIONS})

    target_link_libraries(generator.exe ${OPENGL_LIBRARIES} ${GLUT_LIBRARIES} ${GLEW_LIBRARIES} ${IL_LIBRARIES})
    target_link_libraries(engine.exe ${OPENGL_LIBRARIES} ${GLUT_LIBRARIES} ${GLEW_LIBRARIES} ${IL_LIBRARIES})

    if(NOT GLUT_FOUND OR NOT GLEW_FOUND OR NOT IL_FOUND)
        message(ERROR ": GLUT, GLEW or DevIL not found!")
    endif()

else()
    # macOS specific configurations
    message(STATUS "Configuring for macOS")

    set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Wall -Wextra")
    find_package(GLUT REQUIRED)
    find_package(DevIL REQUIRED)
    include_directories(${GLUT_INCLUDE_DIR} ${IL_INCLUDE_DIR})
    link_directories(${GLUT_LIBRARY_DIRS} ${IL_LIBRARY_DIRS})
    add_definitions(${GLUT_DEFINITIONS})

    target_link_libraries(generator.exe ${OPENGL_LIBRARIES} ${GLUT_LIBRARIES} ${IL_LIBRARIES})
    target_link_libraries(engine.exe ${OPENGL_LIBRARIES} ${GLUT_LIBRARIES} ${IL_LIBRARIES})

    if(NOT GLUT_FOUND OR NOT IL_FOUND)
        message(ERROR ": GLUT or DevIL not found!")
    endif()

endif(WIN32)
