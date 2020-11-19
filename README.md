<a href="https://www.statuscake.com" title="Website Uptime Monitoring"><img src="https://app.statuscake.com/button/index.php?Track=5743819&Days=1&Design=1" /></a>
[![Build Status](https://travis-ci.com/guberArmin/devops-exam.svg?token=m6BpjWymm3UWnZ6QxDwC&branch=main)](https://travis-ci.com/guberArmin/devops-exam)


# Devops 2020 eksamen - Aplikasjon
Dette er aplikasjon repository til `Devops i Skyen [PGR301-1 20H]` eksamen

## Krav til leveranse
- [x] Besvarelsen skal bestå av en tekstfil med lenke til to repositories. Ett repo for applikasjon, og et for infrastruktur:
    - Tekstfil fil lastet opp i besvarelsen

## Krav til applikasjonen:
- [x] Applikasjonen skal eksponere et REST API og ha en database, gjerne "in memory" for eksempel H2
    - Applikasjonen oppfyller alle kravende, til s
- [x] Applikasjonen skal bygge med Maven eller Gradle 
    - Jeg har valgt maven
- [x] Applikasjonen kan skrives i Java eller Kotlin
    - Utviklingsspråket er Java
- [x] Applikasjonen skal ha tester for REST APIet. For eksempel ved hjelp av RestAssured
    - Flere meningsfulle tester skrevet med RestAssured
- [x] Dersom noen av testene feiler, skal bygget også feile
    - Krave er oppfylt, applikasjon bygges og deployes i 2 stages i travis. Første stege er å bygge applikasjon, andre er å 
    deploye den hvis første stege er `passing` 
- [x] Applikasjonen skal være skrevet på en slik måte at drift og vedlikehold er enkelt og i henhold til prinsipper i the "twelve factor app"
To prinsipper gjelder for eksamen *
     - [x] III Config. Ingen hemmeligheter eller konfigurasjon i applikasjonen (ingen config filer med passord/brukere/URLer osv). 
     Vi har lært teknikker i faget for å eksternalisere konfigurasjon. Pass godt på å ikke sjekke inn API nøkler osv.
     - [x] XI Logs. Applikasjonen skal bruke et rammeverk for logging, og logge til standard-out,
ikke til filer. I praksis vil dette si bruk av Logback eller Log4j via sl4j i Spring Boot, med en
"Console appender". Ikke bruk System.out.println();
## Oppgave 1 - Docker
 - Alle kravene er oppfylt og i tilegg deployer jeg docker image til `docker hub`.
 For oppsett av hemligheter [gå til bruksanvisning](#Bruksanvisning)

- Naming convention: `InfluxDB - http_server_requests` i følge [micrometers.io](https://micrometer.io/docs/concepts#_timers)
- endpoints

## Oppgave 2 - Metrikker
- Alle kravene i oppgaven er oppfylt. 
- Når det gjelder `LongTaskTimer` var litt vanskelig å finne en ekte situasjon hvor det kan ta så 
lang tid for min applikasjon å svare.
Derfor valgte jeg å ha bare en timeout på 5 sekunder, på en av endpoints for å 
simulere lang respons tid.
- Siden applikasjon i container kjører uten influxDB jeg anbefaler at det leggess `logging.level.io.micrometer.influx:off` 
i `src/main/resources/application.properties` for at man skal kunne se logging på riktig måten.
- For detaljert informasjon om endpoints og payload [gå til bruksanvisning](#Bruksanvisning).

## Oppgave 3 - Logger
 - Alle kravene i oppgaven er oppfylt. For oppsett av secrets [gå til bruksanvisning](#Bruksanvisning)
# Bruksanvisning
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
