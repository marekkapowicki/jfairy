package org.jfairy.producer.person.locale.pl

import org.jfairy.producer.BaseProducer
import org.jfairy.producer.DateProducer
import org.jfairy.producer.person.Person
import org.joda.time.DateTime
import spock.lang.Specification
import spock.lang.Unroll

import static org.jfairy.producer.person.locale.pl.Pesel.isValid

class PeselSpec extends Specification {

	def randomGenerator = Mock(BaseProducer);
	def dateGenerator = Mock(DateProducer);
	def Pesel generator = new Pesel(dateGenerator, randomGenerator);

	@Unroll
	def "should validate #pesel as #valid"() {

		isValid(pesel) == valid

		where:
		pesel         | valid
		"13881939620" | true
		"00421297469" | true
		"99010147513" | true
		"44051401359" | true
		"44051401358" | false
		"44051401458" | true
		"44052401458" | false

	}

	@Unroll
	def "should generate good pesel"() {
		expect:
		def pesel = generator.generate(DateTime.parse(date), Person.Sex.MALE);

		pesel.startsWith(prefix)
		isValid(pesel)

		where:
		date         | prefix
		"1999-01-01" | "990101"
		"1900-12-31" | "001231"
		"1800-01-31" | "008131"
		"2100-11-01" | "005101"
		"2199-01-11" | "994111"
	}

}
