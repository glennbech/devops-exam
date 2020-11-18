<a href="https://www.statuscake.com" title="Website Uptime Monitoring"><img src="https://app.statuscake.com/button/index.php?Track=5743819&Days=1&Design=1" /></a>
[![Build Status](https://travis-ci.com/guberArmin/devops-exam.svg?token=m6BpjWymm3UWnZ6QxDwC&branch=main)](https://travis-ci.com/guberArmin/devops-exam)

- Naming convention: `InfluxDB - http_server_requests` i følge [micrometers.io](https://micrometer.io/docs/concepts#_timers)


- LEGG TIL logz.io token inn som miljø variable til terraform og lokal
- Lage logg eksempler
- Deploy til flere providers
- add status key to google cloud secrets
- enable influx
- prøve seg på: [google cloud uptime check](https://registry.terraform.io/providers/hashicorp/google/latest/docs/resources/monitoring_uptime_check_config)
- public repoer
- nevne det om feil meldinger influx: logging.level.io.micrometer.influx:off`
- opsgenie:
    - Read, Create and Update, Delete, Configuration Access for api key
    - Grunnen jeg valgte opsgenie er integrasjon med statuscake:
    https://docs.opsgenie.com/docs/statuscake-integration
    StatusCake is a SaaS based web site monitoring service, 
    providing you various kinds of statistics, analytics and 
    information about your website`s downtime. 
    Opsgenie is an alert and notification management solution 
    that is highly complementary to StatusCake.
    
    - Status cake integration https://docs.opsgenie.com/docs/statuscake-integration#add-statuscake-integration-in-opsgenie
