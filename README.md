# cookiecutter-rawdata-converter-app

Seed for generating new Rawdata Converter applications using [cookiecutter](https://cookiecutter.readthedocs.io/en/1.7.2/).

## Usage

### Run with Docker (does not require cookiecutter installation on local machine)

```sh
# Basic usage (You will be prompted to provide basic information about your application)
$ ./bin/cookiecutter-docker.sh


# Additional arguments are available
$ ./bin/cookiecutter-docker.sh --help
    Usage: ./cookiecutter-docker.sh [OPTIONS]

Options:
    -b, --build    Build Docker image before running cookiecutter
    -t, --template Specify custom cookiecutter template via a URI to a git repo
                    e.g. https://github.com/statisticsnorway/cookiecutter-rawdata-converter-app.git
                    Defaults to template in current working directory
    -h, --help     Show this message and exit
```

### Run with cookiecutter installed on local machine

Using cookiecutter without cloning this repo:

```sh
$ cookiecutter gh:statisticsnorway/cookiecutter-rawdata-converter-app
```

...or from a local clone of this repo:

```sh
$ cookiecutter path/to/cookiecutter-rawdata-converter-app
```


## To install cookiecutter

* Requires python3

```sh
$ pip install cookiecutter
```
or
```
$ brew install cookiecutter
```
using pip is recommended, and you need to make sure the latest cookiecutter is installed to support case conversion.

```
$ pip install --upgrade cookiecutter
```

you might need to install jinja2.js
```
$ pip install -U Jinja2
```

