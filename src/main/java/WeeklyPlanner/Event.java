package WeeklyPlanner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event extends AbstractPersistable<Long> {

    @Column(nullable = false)
    private String nimi;

    @Column(nullable = false)
    private String paivamaara;

    @Column(nullable = false)
    private String aika;

    @ManyToOne
    @JoinColumn(name= "category_id", nullable = false) // yhdistää tämän eventin kategoria tauluun
    private Category category;
}

// nullable = false on siksi, että se estää vahingossa tapahtuvat
//virheet jos kenttä jätetään tyhjäksi


//spring.datasource.url=jdbc:h2:file:./database;create=true
//spring.jpa.hibernate.ddl-auto=update
//spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect

//spring.application.name=harjoitustyo_palvelin
//spring.datasource.url=jdbc:h2:mem:testdb
//spring.datasource.driverClassName=org.h2.Driver
//spring.datasource.username=sa
//spring.datasource.password=sa
//spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
//spring.h2.console.enabled=true
