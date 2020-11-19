<a href="https://www.statuscake.com" title="Website Uptime Monitoring"><img src="https://app.statuscake.com/button/index.php?Track=5743819&Days=1&Design=1" /></a>
[![Build Status](https://travis-ci.com/guberArmin/devops-exam.svg?token=m6BpjWymm3UWnZ6QxDwC&branch=main)](https://travis-ci.com/guberArmin/devops-exam)


# Devops 2020 eksamen - Applikasjon
Dette er applikasjon repository til `Devops i Skyen [PGR301-1 20H]` eksamen.

Infrastruktur repoen finnes [her](https://github.com/guberArmin/eksamen-infrastructure).

# Innholdsfortegnelse
- [Innholdsfortegnelse](#innholdsfortegnelse)
  * [Krav til leveranse](#krav-til-leveranse)
  * [Krav til applikasjonen](#krav-til-applikasjonen)
  * [Oppgave 1 - Docker](#oppgave-1---docker)
  * [Oppgave 2 - Metrikker](#oppgave-2---metrikker)
  * [Oppgave 3 - Logger](#oppgave-3---logger)
  * [Oppgave 4 - Infrastruktur](#oppgave-4---infrastruktur)
  * [Oppgave 5 og Overvåkning og varsling](#oppgave-5-og-overvåkning-og-varsling)
  * [Oppgave 6 Valgfri IAC (infrastructure as code)](#oppgave-6-valgfri-iac)
- [Bruksanvisning](#bruksanvisning)
  * [Konfigurasjon av hemligheter - applikasjon](#konfigurasjon-av-hemligheter---applikasjon)
  * [API design og bruk av API](#api-design-og-bruk-av-api)
    * [Kort om API](#kort-om-api)
    * [API calls med eksempel data](#api-calls-med-eksempel-data)
    

## Krav til leveranse
- [x] Besvarelsen skal bestå av en tekstfil med lenke til to repositories. Et repo for applikasjon, og et for infrastruktur:
    - Tekstfil lastet opp i besvarelsen

## Krav til applikasjonen:
- [x] Applikasjonen skal eksponere et REST API og ha en database, gjerne "in memory" for eksempel H2
    - Applikasjonen oppfyller alle overnevnte krav.
- [x] Applikasjonen skal bygge med Maven eller Gradle 
    - Jeg har valgt maven
- [x] Applikasjonen kan skrives i Java eller Kotlin
    - Utviklingsspråket er Java
- [x] Applikasjonen skal ha tester for REST APIet. For eksempel ved hjelp av RestAssured
    - Flere meningsfulle tester skrevet med RestAssured
- [x] Dersom noen av testene feiler, skal bygget også feile
    - Kravet er oppfylt, applikasjon bygges og deployes i 2 stages i travis. Første steg er å bygge applikasjon, andre er å 
    deploye den hvis første steg er `passing` 
- [x] Applikasjonen skal være skrevet på en slik måte at drift og vedlikehold er enkelt og i henhold til prinsipper i the "twelve factor app"
To prinsipper gjelder for eksamen *
     - [x] III Config. Ingen hemmeligheter eller konfigurasjon i applikasjonen (ingen config filer med passord/brukere/URLer osv). 
     Vi har lært teknikker i faget for å eksternalisere konfigurasjon. Pass godt på å ikke sjekke inn API nøkler osv.
     - [x] XI Logs. Applikasjonen skal bruke et rammeverk for logging, og logge til standard-out,
ikke til filer. I praksis vil dette si bruk av Logback eller Log4j via sl4j i Spring Boot, med en
"Console appender". Ikke bruk System.out.println();
## Oppgave 1 - Docker
 - Alle kravene er oppfylt og i tillegg deployer jeg docker image til `docker hub`.
 For oppsett av hemmeligheter [gå til bruksanvisning](#docker)

## Oppgave 2 - Metrikker
- Alle kravene i oppgaven er oppfylt. 
- Når det gjelder `LongTaskTimer` var det litt vanskelig å finne en ekte situasjon hvor det kan ta så 
lang tid for min applikasjon å svare.
Derfor valgte jeg å ha bare en timeout på 5 sekunder, på en av endpoints for å 
simulere lang responstid.
- Siden applikasjon i container kjører uten influxDB har jeg laget egen profil for spring boot for 
denne tilfelle. Når container bygges, da brukes det `application-prod.properties` ved å legge 
`-Dspring.profiles.active=prod` i Dockerfile `Entrypoint`. I `application-prod.properties` slår jeg av 
influxDB logging for å slippe å ha masse errors, og for at logging skal være meningsfull.
- Når det gjelder navngivning konvensjoner har jeg brukt underscore mellom ord  (`http_server_requests`) 
basert på dokumentasjon: [micrometers.io](https://micrometer.io/docs/concepts#_naming_meters)
- For detaljert informasjon om endpoints og payload [gå til bruksanvisning](#api-calls-med-eksempel-data).

## Oppgave 3 - Logger
 - Alle kravene i oppgaven er oppfylt. 
 - Jeg har valgt å logge alt som er info eller høyre nivå.
 - For oppsett av hemmeligheter [gå til bruksanvisning](#Bruksanvisning)

## Oppgave 4 - Infrastruktur
 - Alle kravene er oppfylt.
 - Repoet til infrastrukturen finner man [her](https://github.com/guberArmin/eksamen-infrastructure)

## Oppgave 5 og Overvåkning og varsling
 - Alle kravene er oppfylt.  
 - For oppsett av hemmeligheter [gå til infrastruktur repoen](https://github.com/guberArmin/eksamen-infrastructure)

## Oppgave 6 Valgfri IAC
- Her valgte jeg å bruke [opsgenie](https://registry.terraform.io/providers/opsgenie/opsgenie/latest/docs)
- Det er ganske kraftig verktøy som kan gjøre varsling og schedueling. Vi kan opprete brukere og legge dem
 i lag. Tildele dem forskjellige skift, slik at de har ansvar for drift av aplikasjon i en vis periode osv.
- En av grunene at jeg har valgt opsgenie er at den kan intregeres med status cake
Siden vi har allerede brukt statuscake, i oppgaven 5, tenkte jeg at det var en bra måte å utvide på oppgaven.
- Mer om hvordan man kan integrere statuscake med opsginie finnes [her](https://docs.opsgenie.com/docs/statuscake-integration).
Opsette er ikke vanskelig, og er godt dokumentert.
- En av ulemper med opsgenie er at det er ikke slik at alt av funskjonaliteten er gratis.
Men jeg tror at jeg har klart å bruke de som er gratis på en fornuftig måte, slik at jeg kan vise forståelset 
for opsgenie og noen av bruksområder til den.
- Flere detaljer om selve infrastrukturen finnes på [infrastruktur repoen](https://github.com/guberArmin/eksamen-infrastructure)
 
 ```
    StatusCake is a SaaS based web site monitoring service, providing you various kinds of statistics, 
    analytics and information about your website`s downtime. Opsgenie is an alert and notification 
    management solution that is highly complementary to StatusCake.
```

# Bruksanvisning

## Konfigurasjon av hemligheter - applikasjon

- Før man skal legge in nye hemligheter, må man slette alle globale variable
som er `seure`
- <a name="docker">Docker hub username:</a> `travis encrypt DOCKER_USERNAME=<your_username> --add`
- Docker hub : `travis encrypt DOCKER_KEY=<your-key> --add`
    - For å opprete nøkkelen til Docker hub:
        - Trykk på profil bruker navn opp til høyre
        - `Account settings`
        - `Security`
        - `New access token`
        - Velg navn og trykk `Create`
- GCP service account credentials: `travis encrypt-file <file-name>.json --add`
    - Service account må have følgende tilatelser (roles):
         - Service Account User
         - Cloud Run Admin
         - Storage Admin
- I `.travis.yml` filen må man endre de globale variablene:
    - GCP_PROJECT_ID=`<gcp-project-id>`
    - IMAGE=`gcr.io/<gcp-project-id>/<image-name>`
    
- logz.io token: `travis encrypt LOGZ_TOKEN=<your-token> --add`

## API design og bruk av API

### Kort om API
Jeg har valgt å lage en enkel RESTful API i SpringBoot. Her kan man oprette brukere, tildele dem
tilfeldige kort (cca. 100 forskjellige kort). Se bildene til kort (bilder er 
egentlig svg streng). Siden det vær ikke fokuset på selve applikasjonen har jeg
ikke brukt tid på sikkerhet eller GUI.

### API calls med eksempel data

For å teste API har jeg brukt [postman](https://www.postman.com/).

- `GET /api`
    - Returnerer 200 med `Wellcome to homepage` streng. Brukes som endpoint som
    statuscake skal kontrolere
    
- `POST /api/user`
    - Brukes for å opprete ny bruker.
      
          Forventet payload:          
                    
                  {
                        "userName": "user",
                        "email": "jason@test.com",
                        "name": "Name",
                        "lastName": "LastName"
                  }
          
- `GET /api/user/{userName}`
    - Returnerer data om brukeren med userName

- `POST /api/user/card`
    - Hent tilfeldig kort, og legg det til på listen av kort, brukeren med 
     `userName` eier.
    
            Forventet payload er: {"userName": "new123"}


- `GET /api/imgs/{imgId}`
    - Hent bilde med imgId (og vise den fram i browseren).

- `GET /api/reflect/{msg}`
    - Returnerer `msg` tilbake til brukeren.
      Dette er endpoint som brukes for å simulere stor belasting på serveren,
      som fører til lang beregningstid. Grunnen til at jeg har den er for å
      bruke `LongTaskTimer`. 