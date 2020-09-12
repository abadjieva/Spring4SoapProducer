package com.concretepage.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Type;

/**
 * AVS_MAPPIS<br>
 * de.avs.mappis.domainmodel.entities Partnerfirma.java <br>
 * Entity zur Tabelle PARTNERFIRMA
 * 
 * @author Rene Fuessel, AVS GmbH<br>
 *         Modifikations: Iva Abadjieva, AVS GmbH, Maerz 2012
 * @version 1.0<br>
 */
@Entity
/*
 * @Table(name = Constants.TABLE_NAME_PARTNERFIRMA, catalog = Constants.DATABASE_SCHEMA,
 * uniqueConstraints = { @UniqueConstraint(columnNames = {
 * "FREMDSYSTEM_ID", "PFTYP" }) })
 */
// @Table(name = Constants.TABLE_NAME_PARTNERFIRMA, catalog = Constants.DATABASE_SCHEMA)
@Table(name = "PARTNERFIRMA")
// @Indexed
// @Analyzer(impl = org.apache.lucene.analysis.standard.StandardAnalyzer.class)
//@NamedQueries({
//@NamedQuery(name = "Partnerfirma.getAllePartnerfirmenProMandant", query = "SELECT p FROM Partnerfirma p Where p.pftyp = ? and p.mandantId = ?")})
public class Partnerfirma implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	// @DocumentId
	private long partnerfirmaId;

	@Column(name = "MANDANT_ID", nullable = false)
	private long mandantId;

	@Column(name = "FREMDSYSTEM_ID", nullable = false)
	private long fremdsystemId; // Partnerfirma_id bei PF und ID bei Gastro

	@Column(name = "VS_PARTNERFIRMA_NUMMER", length = 10)
	// @Field(index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.YES, store =
	// Store.NO)
	private String vsPartnerfirmaNummer; // Partnerfirma_nr nur bei PF

	@Column(name = "ZUGEORDNETE_PARTNERFIRMA_NUMMER", length = 10)
	// @Field(index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.YES, store =
	// Store.NO)
	private String zugeordnetePartnerfirmaNummer; // Partnerfirma_nr nur bei
													// Gastro

	@Column(name = "UEBERGEORDNET_ID")
	private Long uebergeordnetId; // uebergeordnetId = fremdsystemId und
									// STATUS_GASTRO = 0 bei PF gibt
									// uebergeordnete PF wieder

	@Temporal(TemporalType.DATE)
	@Column(name = "FREMDSYSTEM_GEAENDERT_AM", length = 10)
	private Date fremdsystemGeaendertAm; // wichtig fuer den Update der Saetze aus dem
	// Partnerfirma-Kategorie-Warengruppe-Komplex (VS - Markus Teufel)

	@Temporal(TemporalType.DATE)
	@Column(name = "FREMDSYSTEM_GEAENDERT_AM_VORHER", length = 10)
	private Date fremdsystemGeaendertAmVorher; // wichtig fuer die Geschichte der Hierarchie
												// Aenderung

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "GEAENDERT_AM")
	private Date geaendertAm; // wird vom Werbemodul verwaltet

	@Column(name = "GEAENDERT_VON")
	private Long geaendertVon; // userId - wird vom Werbemodul verwaltet

	@Column(name = "UNTERNEHMEN_EBENE")
	private Integer unternehmenEbene; // Gesamtanzahl der Ebenes des Unternehmens
										// dem die PF angehoert
	@Column(name = "PARTNERFIRMA_EBENE")
	private Integer partnerfirmaEbene; // Ebene der PF im Unternehmen

	@Column(name = "PFTYP", nullable = false)
	private int pftyp; // 0 = PF aus Vorsystem / 1 = PF aus Gastro-Import

	@Column(name = "PF_HIERARCHIE", length = 400)
	private String pfHierarchie; // auf der Basis von fremdsystemId

	@Column(name = "PF_HIERARCHIE_VORHER", length = 400)
	private String pfHierarchieVorher; // hier die vorherige Hierarchie, wenn Unterschiede ermittelt

	@Column(name = "HIERARCHIE_ERMITTELT")
	private boolean hierarchieErmittelt;

	@Column(name = "PFTYP_UNTERNEHMEN")
	private boolean pftypUnternehmen; // PF ist ein Unternehmen

	@Column(name = "PFTYP_UNTERNEHMEN_VORHER")
	private boolean pftypUnternehmenVorher; // PF ist ein Unternehmen

	@Column(name = "PFTYP_ZENTRALE")
	private boolean pftypZentrale; // PF ist eine Zentrale

	@Column(name = "PFTYP_ZENTRALE_VORHER")
	private boolean pftypZentraleVorher; // PF ist eine Zentrale

	@Column(name = "PFTYP_STANDORT")
	private boolean pftypStandort; // PF ist ein Standort

	@Column(name = "PFTYP_STANDORT_VORHER")
	private boolean pftypStandortVorher; // PF ist ein Standort

	@Column(name = "KZ_GELOESCHT")
	private boolean kzgeloescht;

	@Column(name = "KZ_NEU")
	private boolean kzneu; // Nur bei PF PF_NEU und bei Gastro false oder selber
							// ermitteln?
	@Column(name = "KZ_NEU_PFSTANDORT")
	private boolean kzneuStandort;

	@Column(name = "NAME", length = 100)
	// @Field(index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.YES, store =
	// Store.NO)
	private String name; // NAME_INTERNET bei PF und Restaurant bei Gastro

	@Column(name = "NAMENSZUSATZ", columnDefinition = "LONGTEXT", length = 500)
	// @Field(index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.YES, store =
	// Store.NO)
	private String namenszusatz; // ZUSATZ1 bei PF und Namenszusatz bei Gastro

	@Column(name = "ANZEIGE_INTERNET", nullable = false)
	private boolean anzeigeInternet; // ANZEIGE_INTERNET bei PF

	// @TODO spaeter
	// private AnzeigeKonditinen anzeigeKonditinen; //ANZEIGE_KONDITIONEN bei PF

	@Temporal(TemporalType.DATE)
	// @Column(name = "STARTDATUM", nullable = false, length = 10 ... es gibt null-Werte im VS)
	@Column(name = "STARTDATUM", length = 10)
	private Date startdatum; // VERTRAGSBEGINN bei PF und Startdatum bei Gastro

	@Temporal(TemporalType.DATE)
	@Column(name = "ENDDATUM", length = 10)
	private Date enddatum; // VERTRAGSENDE bei PF und Enddatum bei Gastro

	@Column(name = "ADR_STRASSE", length = 150)
	// @Field(index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.YES, store =
	// Store.NO)
	private String strasse; // STRASSE + HAUSNUMMER bei PF und Strasse (enthaelt
							// bereits Hausnummer) bei Gastro

	@Column(name = "ADR_PLZ", length = 10)
	// @Field(index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.YES, store =
	// Store.NO)
	private String plz; // PLZ bei PF und PLZ bei Gastro

	@Column(name = "ADR_ORT", length = 100)
	// @Field(index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.YES, store =
	// Store.NO)
	private String ort; // ORT bei PF und Ort bei Gastro

	@Column(name = "ADR_ORTSTEIL", length = 100)
	private String ortsteil; // ORTSTEIL bei PF und Stadtteil bei Gastro

	@Column(name = "ADR_LAND_ID")
	private Integer landId; // ADRESSE_ID --> Tabelle LAND --> Column LAND_ID und
	// bei Gastro 0 = Deutschland eintragen

	@Column(name = "ADR_EINKAUFSZENTRUM", length = 100)
	private String einkaufszentrum; // EINKAUFSZENTRUM bei PF und NULL bei
									// Gastro

	@Column(name = "ADR_LATITUDE")
	private Double latitude; // LATITUDE bei PF und muss bei Gastro ueber
								// Adresse ermittelt werden

	@Column(name = "ADR_LONGITUDE")
	private Double longitude; // LONGITUDE bei PF und muss bei Gastro ueber
								// Adresse ermittelt werden
	@Column(name = "KOM_TELEFON", length = 100)
	private String telefon; // TEL bei PF und Telefon ei Gastro

	@Column(name = "KOM_URL", length = 250)
	private String url; // WWW bei PF und URL bei Gastro

	@Column(name = "KOM_EMAIL", length = 100)
	private String email; // NULL bei PF und Email bei Gastro

	// private RestaurantKategorie restaurantKategorie; //Nur bei Gastro
	// Kategorie

	@Column(name = "OEFFNUNGSZEITEN", columnDefinition = "LONGTEXT", length = 2000)
	private String oeffungzeiten; // Nur bei Gastro Oeffnungszeiten

	@Column(name = "WARENGRUPPEN_SAMMELTEXT", columnDefinition = "LONGTEXT", length = 1000)
	private String warengruppeSammeltext; // Nur bei PF WARENGRUPPEN_SAMMELTEXT

	@Column(name = "MINDESTEINKAUF", length = 10)
	private String mindesteinkauf; // Nur bei PF MINDESTEINKAUF

	@Column(name = "AUSSCHLUSS", length = 500)
	private String ausschluss; // Nur bei PF AUSSCHLUSS

	@Column(name = "ANZEIGE_BONUS", nullable = true)
	private Integer anzeigeBonus; // Nur bei PF ANZEIGE_BONUS

	@Column(name = "MAX_BONUS")
	private Double maxBonus; // Nur bei PF MAX_BONUS

	@Column(name = "VERFAHREN_ID")
	private Integer verfahrenId; // Uebertragen aus dem VS fuer den Werbemodul
	// Abrechnungsverfahren (Tab.VERFAHREN)

	@Column(name = "RESTO_ID", length = 100)
	private String restoId; // wird durch den Gastroguide-Importer gefuellt

	/*
	 * Nur bei PF
	 * die PF W_A_*, PF W_B_* oder PF W_C_*
	 */

	@Column(name = "SUCHE", length = 1500)
	private String suche; // fuer das Trigger-Konzept


	public Partnerfirma(){}

	/**
	 * @return the partnerfirmaId
	 */
	public long getPartnerfirmaId(){
		return partnerfirmaId;
	}

	/**
	 * @param partnerfirmaId
	 *            the partnerfirmaId to set
	 */
	public void setPartnerfirmaId(long partnerfirmaId){
		this.partnerfirmaId = partnerfirmaId;
	}

	/**
	 * @return the mandantId
	 */
	public long getMandantId(){
		return mandantId;
	}

	/**
	 * @param mandantId
	 *            the mandantId to set
	 */
	public void setMandantId(long mandantId){
		this.mandantId = mandantId;
	}

	/**
	 * @return the fremdsystemId
	 */
	public long getFremdsystemId(){
		return fremdsystemId;
	}

	/**
	 * @param fremdsystemId
	 *            the fremdsystemId to set
	 */
	public void setFremdsystemId(long fremdsystemId){
		this.fremdsystemId = fremdsystemId;
	}

	public String getVsPartnerfirmaNummer(){
		return vsPartnerfirmaNummer;
	}

	public void setVsPartnerfirmaNummer(String vsPartnerfirmaNummer){
		this.vsPartnerfirmaNummer = vsPartnerfirmaNummer;
	}

	public String getZugeordnetePartnerfirmaNummer(){
		return zugeordnetePartnerfirmaNummer;
	}

	public void setZugeordnetePartnerfirmaNummer(String zugeordnetePartnerfirmaNummer){
		this.zugeordnetePartnerfirmaNummer = zugeordnetePartnerfirmaNummer;
	}

	public Long getUebergeordnetId(){
		return uebergeordnetId;
	}

	public void setUebergeordnetId(Long uebergeordnetId){
		this.uebergeordnetId = uebergeordnetId;
	}

	/**
	 * @return the fremdsystemGeaendertAm
	 */
	public Date getFremdsystemGeaendertAm(){
		return fremdsystemGeaendertAm;
	}

	/**
	 * @param fremdsystemGeaendertAm
	 *            the fremdsystemGeaendertAm to set
	 */
	public void setFremdsystemGeaendertAm(Date fremdsystemGeaendertAm){
		this.fremdsystemGeaendertAm = fremdsystemGeaendertAm;
	}

	/**
	 * @return the fremdsystemGeaendertAmVorher
	 */
	public Date getFremdsystemGeaendertAmVorher(){
		return fremdsystemGeaendertAmVorher;
	}

	/**
	 * @param fremdsystemGeaendertAmVorher
	 *            the fremdsystemGeaendertAmVorher to set
	 */
	public void setFremdsystemGeaendertAmVorher(Date fremdsystemGeaendertAmVorher){
		this.fremdsystemGeaendertAmVorher = fremdsystemGeaendertAmVorher;
	}

	/**
	 * @return the geaendertAm
	 */
	public Date getGeaendertAm(){
		return geaendertAm;
	}

	/**
	 * @param geaendertAm
	 *            the geaendertAm to set
	 */
	public void setGeaendertAm(Date geaendertAm){
		this.geaendertAm = geaendertAm;
	}

	/**
	 * @return the geaendertVon
	 */
	public Long getGeaendertVon(){
		return geaendertVon;
	}

	/**
	 * @param geaendertVon
	 *            the geaendertVon to set
	 */
	public void setGeaendertVon(Long geaendertVon){
		this.geaendertVon = geaendertVon;
	}

	/**
	 * @return the anzeigeInternet
	 */
	public boolean isAnzeigeInternet(){
		return anzeigeInternet;
	}

	/**
	 * @param anzeigeInternet
	 *            the anzeigeInternet to set
	 */
	public void setAnzeigeInternet(boolean anzeigeInternet){
		this.anzeigeInternet = anzeigeInternet;
	}

	public int getPftyp(){
		return pftyp;
	}

	public void setPftyp(int pftyp){
		this.pftyp = pftyp;
	}

	public boolean isPftypUnternehmen(){
		return pftypUnternehmen;
	}

	public void setPftypUnternehmen(boolean pftypUnternehmen){
		this.pftypUnternehmen = pftypUnternehmen;
	}

	public boolean isPftypZentrale(){
		return pftypZentrale;
	}

	public void setPftypZentrale(boolean pftypZentrale){
		this.pftypZentrale = pftypZentrale;
	}

	public boolean isPftypStandort(){
		return pftypStandort;
	}

	public void setPftypStandort(boolean pftypStandort){
		this.pftypStandort = pftypStandort;
	}

	/**
	 * @return the kzgeloescht
	 */
	public boolean isKzgeloescht(){
		return kzgeloescht;
	}

	/**
	 * @param kzgeloescht
	 *            the kzgeloescht to set
	 */
	public void setKzgeloescht(boolean kzgeloescht){
		this.kzgeloescht = kzgeloescht;
	}

	/**
	 * @return the kzneu
	 */
	public boolean isKzneu(){
		return kzneu;
	}

	/**
	 * @param kzneu
	 *            the kzneu to set
	 */
	public void setKzneu(boolean kzneu){
		this.kzneu = kzneu;
	}

	/**
	 * @return the kzneuStandort
	 */
	public boolean isKzneuStandort(){
		return kzneuStandort;
	}

	/**
	 * @param kzneuStandort
	 *            the kzneuStandort to set
	 */
	public void setKzneuStandort(boolean kzneuStandort){
		this.kzneuStandort = kzneuStandort;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	/*
	 * @ManyToOne(fetch=FetchType.EAGER)
	 * @JoinColumn(name = "ANZEIGE_KONDITIONEN_ID", nullable = false) public
	 * AnzeigeKonditinen getAnzeigeKonditinen() { return anzeigeKonditinen; }
	 * public void setAnzeigeKonditinen(AnzeigeKonditinen anzeigeKonditinen) {
	 * this.anzeigeKonditinen = anzeigeKonditinen; }
	 */

	public Date getStartdatum(){
		return startdatum;
	}

	public void setStartdatum(Date startdatum){
		this.startdatum = startdatum;
	}

	public Date getEnddatum(){
		return enddatum;
	}

	public void setEnddatum(Date enddatum){
		this.enddatum = enddatum;
	}

	public String getStrasse(){
		return strasse;
	}

	public void setStrasse(String strasse){
		this.strasse = strasse;
	}

	public String getPlz(){
		return plz;
	}

	public void setPlz(String plz){
		this.plz = plz;
	}

	public String getOrt(){
		return ort;
	}

	public void setOrt(String ort){
		this.ort = ort;
	}

	public String getOrtsteil(){
		return ortsteil;
	}

	public void setOrtsteil(String ortsteil){
		this.ortsteil = ortsteil;
	}

	public String getEinkaufszentrum(){
		return einkaufszentrum;
	}

	public void setEinkaufszentrum(String einkaufszentrum){
		this.einkaufszentrum = einkaufszentrum;
	}

	public Double getLongitude(){
		return longitude;
	}

	public void setLongitude(Double longitude){
		this.longitude = longitude;
	}

	public Double getLatitude(){
		return latitude;
	}

	public void setLatitude(Double latitude){
		this.latitude = latitude;
	}

	public String getTelefon(){
		return telefon;
	}

	public void setTelefon(String telefon){
		this.telefon = telefon;
	}

	public String getUrl(){
		return url;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getEmail(){
		return email;
	}

	public void setEmail(String email){
		this.email = email;
	}

	/**
	 * @return the unternehmenEbene
	 */
	public Integer getUnternehmenEbene(){
		return unternehmenEbene;
	}

	/**
	 * @param unternehmenEbene
	 *            the unternehmenEbene to set
	 */
	public void setUnternehmenEbene(Integer unternehmenEbene){
		this.unternehmenEbene = unternehmenEbene;
	}

	/**
	 * @return the partnerfirmaEbene
	 */
	public Integer getPartnerfirmaEbene(){
		return partnerfirmaEbene;
	}

	/**
	 * @param partnerfirmaEbene
	 *            the partnerfirmaEbene to set
	 */
	public void setPartnerfirmaEbene(Integer partnerfirmaEbene){
		this.partnerfirmaEbene = partnerfirmaEbene;
	}

	/**
	 * @return the namenszusatz
	 */
	public String getNamenszusatz(){
		return namenszusatz;
	}

	/**
	 * @param namenszusatz
	 *            the namenszusatz to set
	 */
	public void setNamenszusatz(String namenszusatz){
		this.namenszusatz = namenszusatz;
	}

	public Double getMaxBonus(){
		return maxBonus;
	}

	/**
	 * @param maxBonus
	 *            the maxBonus to set
	 */
	public void setMaxBonus(Double maxBonus){
		this.maxBonus = maxBonus;
	}

	/*
	 * @ManyToOne(fetch=FetchType.EAGER)
	 * @JoinColumn(name = "RESTAURANT_KATEGORIE_ID") public RestaurantKategorie
	 * getRestaurantKategorie() { return restaurantKategorie; } public void
	 * setRestaurantKategorie(RestaurantKategorie restaurantKategorie) {
	 * this.restaurantKategorie = restaurantKategorie; }
	 */

	public String getOeffungzeiten(){
		return oeffungzeiten;
	}

	public void setOeffungzeiten(String oeffungzeiten){
		this.oeffungzeiten = oeffungzeiten;
	}

	public String getWarengruppeSammeltext(){
		return warengruppeSammeltext;
	}

	public void setWarengruppeSammeltext(String warengruppeSammeltext){
		this.warengruppeSammeltext = warengruppeSammeltext;
	}

	public String getMindesteinkauf(){
		return mindesteinkauf;
	}

	public void setMindesteinkauf(String mindesteinkauf){
		this.mindesteinkauf = mindesteinkauf;
	}

	public String getAusschluss(){
		return ausschluss;
	}

	public void setAusschluss(String ausschluss){
		this.ausschluss = ausschluss;
	}

	public Integer getAnzeigeBonus(){
		return anzeigeBonus;
	}

	public void setAnzeigeBonus(Integer anzeigeBonus){
		this.anzeigeBonus = anzeigeBonus;
	}

	/**
	 * @return the landId
	 */
	public Integer getLandId(){
		return landId;
	}

	/**
	 * @param landId
	 *            the landId to set
	 */
	public void setLandId(Integer landId){
		this.landId = landId;
	}

	
	/**
	 * @return the verfahrenId
	 */
	public Integer getVerfahrenId(){
		return verfahrenId;
	}

	/**
	 * @param verfahrenId
	 *            the verfahrenId to set
	 */
	public void setVerfahrenId(Integer verfahrenId){
		this.verfahrenId = verfahrenId;
	}

	/**
	 * @return the restoId
	 */
	public String getRestoId(){
		return restoId;
	}

	/**
	 * @param restoId
	 *            the restoId to set
	 */
	public void setRestoId(String restoId){
		this.restoId = restoId;
	}

	/**
	 * @return the pfHierarchie
	 */
	public String getPfHierarchie(){
		return pfHierarchie;
	}

	/**
	 * @param pfHierarchie
	 *            the pfHierarchie to set
	 */
	public void setPfHierarchie(String pfHierarchie){
		this.pfHierarchie = pfHierarchie;
	}

	/**
	 * @return the hierarchieErmittelt
	 */
	public boolean isHierarchieErmittelt(){
		return hierarchieErmittelt;
	}

	/**
	 * @param hierarchieErmittelt
	 *            the hierarchieErmittelt to set
	 */
	public void setHierarchieErmittelt(boolean hierarchieErmittelt){
		this.hierarchieErmittelt = hierarchieErmittelt;
	}

	/**
	 * @return the pfHierarchieVorher
	 */
	public String getPfHierarchieVorher(){
		return pfHierarchieVorher;
	}

	/**
	 * @param pfHierarchieVorher
	 *            the pfHierarchieVorher to set
	 */
	public void setPfHierarchieVorher(String pfHierarchieVorher){
		this.pfHierarchieVorher = pfHierarchieVorher;
	}

	/**
	 * @return the pftypUnternehmenVorher
	 */
	public boolean isPftypUnternehmenVorher(){
		return pftypUnternehmenVorher;
	}

	/**
	 * @param pftypUnternehmenVorher
	 *            the pftypUnternehmenVorher to set
	 */
	public void setPftypUnternehmenVorher(boolean pftypUnternehmenVorher){
		this.pftypUnternehmenVorher = pftypUnternehmenVorher;
	}

	/**
	 * @return the pftypZentraleVorher
	 */
	public boolean isPftypZentraleVorher(){
		return pftypZentraleVorher;
	}

	/**
	 * @param pftypZentraleVorher
	 *            the pftypZentraleVorher to set
	 */
	public void setPftypZentraleVorher(boolean pftypZentraleVorher){
		this.pftypZentraleVorher = pftypZentraleVorher;
	}

	/**
	 * @return the pftypStandortVorher
	 */
	public boolean isPftypStandortVorher(){
		return pftypStandortVorher;
	}

	/**
	 * @param pftypStandortVorher
	 *            the pftypStandortVorher to set
	 */
	public void setPftypStandortVorher(boolean pftypStandortVorher){
		this.pftypStandortVorher = pftypStandortVorher;
	}

	/**
	 * @return the suche
	 */
	public String getSuche(){
		return suche;
	}

	/**
	 * @param suche
	 *            the suche to set
	 */
	public void setSuche(String suche){
		this.suche = suche;
	}
	
	 @Override
	  public String toString()
	  {
	    return getClass().getName() + "[name=" + name+ "]";
	  }

	 @Override
	public boolean equals(Object obj) {
		 if (obj == null) {
			    return false;
			  }
			  if (obj == this) {
			    return true;
			  }
			  if (obj.getClass() == this.getClass()) {
			    Partnerfirma b = (Partnerfirma)obj;
			    if (this.verfahrenId == b.getVerfahrenId() && this.name.equals(b.getName())) {
			      return true;
			    }
			  }
			  return false;
			}

	 @Override
	public int hashCode() {
		// TODO Auto-generated method stub
		 int hash = 0;
	        hash += this.verfahrenId != null ? this.verfahrenId.hashCode() : 0;
	        return hash;
		
	}
	}



