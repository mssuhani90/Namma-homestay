@rem
@rem Copyright 2022 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (http://www.apache.org/licenses/LICENSE-2.0);
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  Gradle startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
@rem set DIRNAME (Drive:\path\to\dirname in case of spaces)
set DIRNAME=%DIRNAME:\=\\%

@rem Begin setting up classpath
set CLASSPATH=
set BASEDIR=%DIRNAME%

@rem Determine the Java command to use to start the JVM.
if exist "%JAVA_HOME%\jre\sh.java.exe" (
    set _JAVACMD=%JAVA_HOME%\jre\sh.java.exe
) else (
    set _JAVACMD=%JAVA_HOME%\bin\java.exe
)

if exist "%JAVA_HOME%\bin\java.exe" set _JAVACMD=%JAVA_HOME%\bin\java.exe

if exist "%JAVA_HOME%\jre\bin\java.exe" set _JAVACMD=%JAVA_HOME%\jre\bin\java.exe

@rem Prefer 64 bit JVM if available
if exist "%JAVA_HOME%\bin\java.exe" (
    if exist "%JAVA_HOME%\bin\javaw.exe" (
        set _JAVACMD=%JAVA_HOME%\bin\javaw.exe
    )
)

@rem Set JVM options
set DEFAULT_JVM_OPTS=

@rem Find which Java to use
if exist "%JAVA_HOME%\bin\java.exe" goto use_java_home
goto find_jdk

:use_java_home
@rem The JDK is present
goto set_jvm_opts

:find_jdk
@rem Look for a JDK installation directory
set JDK_DIR=
for /d %%i in ("%ProgramFiles%\Java\jdk*") do (
    set JDK_DIR=%%i
    goto set_jvm_opts
)
for /d %%i in ("%ProgramFiles(x86)%\Java\jdk*") do (
    set JDK_DIR=%%i
    goto set_jvm_opts
)
goto no_jdk

:set_jvm_opts
@rem Set standard JVM options
set JVM_OPTS=%DEFAULT_JVM_OPTS% %JAVA_OPTS% %GRADLE_OPTS%

:check_classpath
@rem Check for a gradle wrapper jar
if exist "%DIRNAME%\lib\gradle-wrapper.jar" goto found_wrapper
goto no_wrapper

:found_wrapper
@rem Set wrapper location
set WRAPPER_JAR_PATH=%DIRNAME%\lib\gradle-wrapper.jar

@rem Launch wrapper
"%_JAVACMD%" %JVM_OPTS% ^
    "-Dorg.gradle.appname=%~n0" ^
    -classpath "%WRAPPER_JAR_PATH%" ^
    org.gradle.wrapper.GradleWrapperMain %*

@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:findMain
if exist "%DIRNAME%\lib\gradle-wrapper.jar" goto wrapperClasspath
goto noWrapperClasspath

:wrapperClasspath
set CLASSPATH=%DIRNAME%\lib\gradle-wrapper.jar;%CLASSPATH%
goto executeWrapper

:noWrapperClasspath
echo Unable to find Gradle wrapper jar at ^"%DIRNAME%\lib\gradle-wrapper.jar^", will try to download it.
goto downloadWrapper

:no_jdk
echo No JDK found. Please install JDK 8 or higher.
exit /b 1

:no_wrapper
echo Gradle wrapper not found. Download from https://services.gradle.org/distributions/gradle-8.4-bin.zip and extract to project.
exit /b 1

:executeWrapper
"%_JAVACMD%" %JVM_OPTS% ^
    "-Dorg.gradle.appname=%~n0" ^
    -classpath "%CLASSPATH%" ^
    org.gradle.wrapper.GradleWrapperMain %*

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
