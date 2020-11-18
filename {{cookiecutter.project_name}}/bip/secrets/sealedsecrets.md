# Sealed secrets

The following can be used as guidance in order to wrap misc secrets into "sealedsecrets".

All commands below assumes that you're located at the root of your local `platform-dev` repo.

## Pseudo secrets

### Prod

```sh
mysecret=$(openssl rand -base64 32)
kubectl create secret generic pseudo-secrets-{{cookiecutter.project_slug}} \
-n dapla \
--dry-run=client \
--from-literal=secret1='$mysecret' \
-o yaml | kubeseal --format=yaml --cert ./certs/prod-bip-app.crt > flux/prod-bip-app/dapla/secrets/pseudo/pseudo-secrets-{{cookiecutter.project_slug}}-sealedsecret.yaml
```

### Staging

```sh
mysecret=$(openssl rand -base64 32)
kubectl create secret generic pseudo-secrets-{{cookiecutter.project_slug}} \
-n dapla \
--dry-run=client \
--from-literal=secret1='$mysecret' \
-o yaml | kubeseal --format=yaml --cert ./certs/staging-bip-app.crt > flux/staging-bip-app/dapla/secrets/pseudo/pseudo-secrets-{{cookiecutter.project_slug}}-sealedsecret.yaml
```

## Rawdata encryption

### Prod

```sh
 kubectl create secret generic rawdata-encryption-credentials-{{cookiecutter.rawdata_shortname}} \
-n dapla \
--dry-run=client \
--from-literal=encryptionKey='somekey' \
--from-literal=encryptionSalt='somesalt' \
-o yaml | kubeseal --format=yaml --cert ./certs/prod-bip-app.crt > flux/prod-bip-app/dapla/secrets/rawdata-encryption/rawdata-encryption-credentials-{{cookiecutter.rawdata_shortname}}-sealedsecret.yaml
```

### Staging

```sh
 kubectl create secret generic rawdata-encryption-credentials-{{cookiecutter.rawdata_shortname}} \
-n dapla \
--dry-run=client \
--from-literal=encryptionKey='somekey' \
--from-literal=encryptionSalt='somesalt' \
-o yaml | kubeseal --format=yaml --cert ./certs/staging-bip-app.crt > flux/staging-bip-app/dapla/secrets/rawdata-encryption/rawdata-encryption-credentials-{{cookiecutter.rawdata_shortname}}-sealedsecret.yaml
```

## Data access (service account sealed secrets)

Download SA keys as JSON from the GCP console. The following assumes
that the following files are downloaded:
```
data-access-kilde-{{cookiecutter.rawdata_shortname}}-r-gcs-key.json
data-access-kilde-{{cookiecutter.rawdata_shortname}}-rw-gcs-key.json
```

### Prod

```sh
kubectl create secret generic data-access-kilde-{{cookiecutter.rawdata_shortname}}-r-gcs-key \
-n dapla \
--dry-run=client \
--from-file=path/to/data-access-kilde-{{cookiecutter.rawdata_shortname}}-r-gcs-key.json \
-o yaml | kubeseal --format=yaml --cert ./certs/prod-bip-app.crt > flux/prod-bip-app/dapla/data-access/storage-data-kilde-{{cookiecutter.rawdata_shortname}}/data-access-kilde-{{cookiecutter.rawdata_shortname}}-r-gcs-key-sealedsecret.yaml
```

```sh
kubectl create secret generic data-access-kilde-{{cookiecutter.rawdata_shortname}}-rw-gcs-key \
-n dapla \
--dry-run=client \
--from-file=path/to/data-access-kilde-{{cookiecutter.rawdata_shortname}}-rw-gcs-key.json \
-o yaml | kubeseal --format=yaml --cert ./certs/prod-bip-app.crt > flux/prod-bip-app/dapla/data-access/storage-data-kilde-{{cookiecutter.rawdata_shortname}}/data-access-kilde-{{cookiecutter.rawdata_shortname}}-rw-gcs-key-sealedsecret.yaml
```

### Staging

```sh
kubectl create secret generic data-access-kilde-{{cookiecutter.rawdata_shortname}}-r-gcs-key \
-n dapla \
--dry-run=client \
--from-file=path/to/data-access-kilde-{{cookiecutter.rawdata_shortname}}-r-gcs-key.json \
-o yaml | kubeseal --format=yaml --cert ./certs/staging-bip-app.crt > flux/staging-bip-app/dapla/data-access/storage-data-kilde-{{cookiecutter.rawdata_shortname}}/data-access-kilde-{{cookiecutter.rawdata_shortname}}-r-gcs-key-sealedsecret.yaml
```

```sh
kubectl create secret generic data-access-kilde-{{cookiecutter.rawdata_shortname}}-rw-gcs-key \
-n dapla \
--dry-run=client \
--from-file=path/to/data-access-kilde-{{cookiecutter.rawdata_shortname}}-rw-gcs-key.json \
-o yaml | kubeseal --format=yaml --cert ./certs/staging-bip-app.crt > flux/staging-bip-app/dapla/data-access/storage-data-kilde-{{cookiecutter.rawdata_shortname}}/data-access-kilde-{{cookiecutter.rawdata_shortname}}-rw-gcs-key-sealedsecret.yaml
```