#!/bin/bash


export SCRIPT_DIR=$(dirname ${0})
export CERT_GEN_CMD="${SCRIPT_DIR}/../bin/certstrap --depot-path ${SCRIPT_DIR}/../tls"


function generate_ca() {
	local common_name=${1:-"keycloak"}
	$CERT_GEN_CMD init --common-name $common_name
}


function generate_server_cert() {
	local cn=$1
	shift
	local ip=$1
	shift
	local domain=$1
	shift
	local CA=$1
	shift

	$CERT_GEN_CMD request-cert --common-name $cn --ip $ip --domain $domain
	$CERT_GEN_CMD sign $cn --CA $CA
	openssl pkcs12 -export -out tls/${cn}.p12 -inkey tls/${cn}.key -in tls/${cn}.crt -chain -CAfile tls/${CA}.crt -caname "Self signed CA certificate" -name "server certificate"
	keytool -importkeystore -destkeystore tls/keystore.jks -srckeystore tls/${cn}.p12 -srcstoretype PKCS12 -alias "server certificate" -deststorepass helloworld -srcstorepass helloworld
	keytool -import -trustcacerts -noprompt -alias ca -ext san=dns:localhost,ip:127.0.0.1,ip:${ip},dns:${domain} -file tls/$CA.crt -keystore tls/truststore.jks
}


function generate_user_cert() {
 local cn=$1
 shift
 local CA=$1
 shift

 $CERT_GEN_CMD request-cert --country US --province MD --organization ShahFamily --organizational-unit Parents --common-name ${cn}
 $CERT_GEN_CMD sign $cn --CA $CA
}

action=${1}
$action ${@:2}
