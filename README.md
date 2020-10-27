# cookiecutter-rawdata-converter-app

A seed for generating new rawdata converter applications using [cookiecutter](https://cookiecutter.readthedocs.io/en/1.7.2/).

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

Using cookiecutter without cloning this repo:

```sh
$ cookiecutter gh:statisticsnorway/cookiecutter-rawdata-converter-app
```

...or from you local clone of this repo:

```sh
$ cookiecutter path/to/cookiecutter-rawdata-converter-app
```


## To install cookiecutter on you local machine

* Requires python3 on you local machine.

```sh
$ pip install cookiecutter
```
or
```
$ brew install cookiecutter
```

