# Cloud & DevOps Project

## Introduction
This project demonstrates a CI/CD workflow for deploying a Spring Boot application to Kubernetes using Jenkins. The application is packaged as a Docker image, stored in a container registry, and deployed to Azure Kubernetes Service (AKS). Jenkins is also deployed on Kubernetes and uses a custom Jenkins agent image that includes tooling such as Java, Docker, Kubernetes, and Azure.

The goal of the project is to automate the software delivery lifecycle from source code changes to application deployment. The pipeline covers continuous integration through build and test stages and continuous delivery through image publishing and Kubernetes deployment.

## Application Architecture
The application architecture consists of a Spring Boot application running in Kubernetes, exposed through a Kubernetes Service and load balancer for external access. Jenkins acts as the CI/CD engine and automates building, testing, packaging, and deployment. Docker is used to package the application, and a container registry stores the built images.

Main components:
- Spring Boot application
- Docker
- Kubernetes
- Jenkins controller and Jenkins agent
- Azure Kubernetes Service (AKS)
- Azure service principal for deployment

Architecture diagram:

```text
Developer
   |
   v
Git Repository
   |
   v
Jenkins Pipeline
   |
   |--> Build Java app
   |--> Build Docker image
   |--> Push image to registry
   |--> Deploy to AKS
                |
                v
           Kubernetes
                |
                v
          Spring Boot App
```


## Jenkins CI/CD Pipeline
The Jenkins CI/CD pipeline automates the end-to-end software delivery workflow. When code is pushed to the Git repository, Jenkins retrieves the latest source code and starts the pipeline. The application is then built and tested. If the build is successful, Jenkins creates a Docker image and pushes it to a container registry. After that, Jenkins authenticates to Azure using a service principal and deploys the updated image to AKS.

A typical pipeline flow for this project is:
1. Pull source code from Git
2. Build the Spring Boot application
3. Run automated tests
4. Build the Docker image
5. Push the image to the container registry
6. Authenticate to Azure
7. Deploy the image to Kubernetes
8. Verify rollout status

Pipeline diagram:

```text
Git Push
   |
   v
Jenkins Pipeline
   |
   |--> Checkout source
   |--> Build application
   |--> Run tests
   |--> Build Docker image
   |--> Push image
   |--> Azure login
   |--> Deploy to AKS
                |
                v
           Kubernetes
```

Jenkins can use a custom Jenkins agent image to provide the required command-line tools. This allows builds and deployments to run in a consistent and isolated environment.

## Improvements
1. Build multi-architecture Docker images.
The project encountered an `exec format error`, which indicates an architecture mismatch between the built image and the AKS nodes. Building images for both `linux/amd64` and `linux/arm64` would prevent this issue and improve portability.

2. Separate development and production more clearly.
Using dedicated namespaces, Helm values files, and approval gates would make deployments safer and reduce the risk of accidental production changes.

3. Improve secret management.
Service principal credentials and other sensitive configuration should be stored securely using Kubernetes Secrets or Azure Key Vault instead of being passed manually.

4. Add health checks and deployment safeguards.
Readiness probes, liveness probes, and rollout checks should be added so Kubernetes can better detect unhealthy containers and prevent broken releases.

