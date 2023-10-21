# Bluebird

Tool to put information from a Blackbird into InfluxDB.

## Building

`mvn clean install`

## Configuring

Create a config file `/etc/bluebird.config` and fill the following:
```
bluebird.username=secret@domain.nl
bluebird.password=somesecretpassword
bluebird.apikey=a-very-long-key
bluebird.tokenurl=https://some-url-ending-with/token
bluebird.logurl=https://some-url-ending-with/heatpump_logs?select=*&heatpump_serial_number=eq.<serialnumber>&order=timestamp.desc&limit=1

influxdb.bluebird.measurement=bluebird
influxdb.url=http://your-influx-server:8086
influxdb.username=username
influxdb.password=password
```

## Running

`java -jar target/bluebird-0.1-SNAPSHOT-jar-with-dependencies.jar`