# cokiecutter-rawdata-converter-app

A seed for generating new rawdata converter applications using cookiecutter.

## Usage

### Run with Docker (does not require cookiecutter installation on local machine)

```sh
# Basic usage (You will be prompted to provide basic information about your application)
$ ./cookiecutter-docker.sh


# Additional arguments are available
$ ./cookiecutter-docker.sh --help
    Usage: ./cookiecutter-docker.sh [OPTIONS]

Options:
    -b, --build    Build Docker image before running cookiecutter
    -t, --template Specify custom cookiecutter template via a URI to a git repo
                    e.g. https://github.com/statisticsnorway/cookiecutter-rawdata-converter-app.git
                    Defaults to template in current working directory
    -h, --help     Show this message and exit
```

### Run with cookiecutter installed on local machine

Using 
```sh
$ cookiecutter gh:statisticsnorway/cookiecutter-rawdata-converter-app
```

A 

/*
Requires
Python
Cookiecutter brew install cookiecutter or pip install cookiecutter
JDK 8+
Use cookiecutter-spring-boot
cookiecutter https://github.com/Apeopl/cookiecutter-spring-boot
*/