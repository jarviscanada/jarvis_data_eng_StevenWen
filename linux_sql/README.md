# Linux Cluster Monitoring Agent
This project is under development. Since this project follows the GitFlow, the final work will be merged to the main branch after Team Code Team.

# Introduction
The Linux Cluster Monitoring Agent collects hardware and real-time resource usage data from Rocky Linux servers and stores it in PostgreSQL database. The system uses Bash scripts to gather CPU, memory, and disk metrics, with cron scheduling to automate periodic data collection.
PostgreSQL is provisioned via Docker, and Git manages the project?s codebase. Designed for multi-host clusters, each server runs the same lightweight agent, allowing the LCA team to monitor system health, compare node performance, and run analytical SQL queries for capacity planning and operational insights.

# Quick Start
Use markdown code block for your quick-start commands
- Start a psql instance using psql_docker.sh
- Create tables using ddl.sql
- Insert hardware specs data into the DB using host_info.sh
- Insert hardware usage data into the DB using host_usage.sh
- Crontab setup

''' bash
# Start PostgreSQL container
bash scripts/psql_docker.sh create postgres password

# Create tables
psql -h localhost -U postgres -d host_agent -f sql/ddl.sql

# Insert hardware specs
bash scripts/host_info.sh localhost 5432 host_agent postgres password

# Insert usage data
bash scripts/host_usage.sh localhost 5432 host_agent postgres password

'''
