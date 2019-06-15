package nl.hu.bep.group4.bifi.lader.implementations;

import java.time.Instant;
import nl.hu.bep.group4.bifi.exceptions.GarbageDataException;
import nl.hu.bep.group4.bifi.lader.MongoLader;
import nl.hu.bep.group4.bifi.model.Factuur;
import nl.hu.bep.group4.bifi.model.FactuurRegel;
import nl.hu.bep.group4.bifi.model.FactuurRegel.BTWcode;
import nl.hu.bep.group4.bifi.model.FactuurRegel.Unit;
import nl.hu.bep.group4.bifi.model.Klant;
import nl.hu.bep.group4.bifi.model.Persoon;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoLaderImpl implements MongoLader {
	MongoDatabase db = null;
	Factuur factuur = null;
	/**
	 *\
	 * Mongo deals with errors. Connection does not have to be closed.
	 * @return
	 */
	@SuppressWarnings("squid:S2095")
	public MongoCollection<Document> connectToMongoDB() {
		MongoCollection<Document> mongoCollection = null;
		String database = "BEPBifi";

		MongoClientURI uri = new MongoClientURI("mongodb+srv://dbUser:112112@cluster0-vk3z3.mongodb.net/test?retryWrites=true");
		MongoClient mongoClient = new MongoClient(uri);
		 db = mongoClient.getDatabase(database);
		 mongoCollection = db.getCollection("BEPBifi");

		return mongoCollection;
	}

	/**
	 * Accepteert maandnummer (0-11)
	 */
	@Override
	public List<Factuur> getFacturenVoorMaand(int maandNummer) throws GarbageDataException {
		
		List<Factuur> facturen = new ArrayList<>();

		MongoCollection<Document> collection = connectToMongoDB();
		Iterator<Document> it = collection.find().iterator();
		while(it.hasNext()) {
			
			Document factuurVanMongo = it.next();
			
			Date date = factuurVanMongo.getDate("date");
			Calendar factuurDate = Calendar.getInstance();
			factuurDate.setTime(date);
			if (factuurDate.get(Calendar.MONTH) == maandNummer) {
				setFactuurSettings(date, factuurVanMongo);
				fillFacturenList(facturen, factuurVanMongo);
			}
		}
		return facturen;	

	}

	private ArrayList<Factuur> fillFacturenList(List<Factuur> facturen, Document factuurVanMongo) throws GarbageDataException {

		List<FactuurRegel> factuurRegels = new ArrayList<>();
		List<Document> linesOfFactuur = factuurVanMongo.getList("invoiceLines", Document.class);
		for(Document line : linesOfFactuur) {
			FactuurRegel factuurRegel = new FactuurRegel();
			factuurRegel.setAantal(line.getInteger("quantity"));
			factuurRegel.setProductID(line.getInteger("productId"));
			factuurRegel.setProductNaam(line.getString("productName"));
			Object prijsObj = line.get("totalPrice");
			if(prijsObj instanceof Double) {
				factuurRegel.setTotaalprijsExBTW((Double)prijsObj);
			} else if(prijsObj instanceof Integer) {
				factuurRegel.setTotaalprijsExBTW(((Integer)prijsObj).doubleValue());
			} else {
				throw new GarbageDataException("TotalPrice heeft als type "+prijsObj.getClass().getName());
			}

			String unit = line.getString("unit").toLowerCase();
			if(unit.contentEquals("kg")) {
				factuurRegel.setUnit(Unit.KILOGRAM);
			} else {
				throw new GarbageDataException(unit + " is geen bestaande unit");
			}

			String btwCode = line.getString("btwCode").toLowerCase();
			switch (btwCode) {
				case "hoog":
				case "high":
					factuurRegel.setBtwCode(BTWcode.HOOG);
					break;
				case "laag":
				case "low":
					factuurRegel.setBtwCode(BTWcode.LAAG);
					break;
				case "geen":
				case "none":
					factuurRegel.setBtwCode(BTWcode.GEEN);
					break;
				default:
					throw new GarbageDataException("Onbekende btwCode " + btwCode);
			}
			factuurRegels.add(factuurRegel);
		}
		factuur.setFactuurregels(factuurRegels);
		facturen.add(factuur);
		return (ArrayList<Factuur>) facturen;
	}
  
	private void setFactuurSettings(Date date, Document factuurVanMongo) {

		Instant instant = date.toInstant();
		this.factuur = new Factuur();
		factuur.setDatumtijd(instant.toString());
		factuur.setFactuurNummer(factuurVanMongo.getInteger("invoiceId"));
		factuur.setOpmerking(factuurVanMongo.getString("note"));
		factuur.setContactPersoon(new Persoon(factuurVanMongo.getInteger("personId")));
		factuur.setKlant(new Klant(factuurVanMongo.getInteger("customerId")));
	}
}