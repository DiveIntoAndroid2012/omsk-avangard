package ru.omsu.avangard.omsk.services.protocol;

import java.io.Serializable;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.google.gson.annotations.SerializedName;
import com.stanfy.gsonxml.GsonXml;
import com.stanfy.gsonxml.GsonXmlBuilder;
import com.stanfy.gsonxml.XmlParserCreator;

public class Responses {

	public static abstract class Response implements Serializable {
		private static final long serialVersionUID = -1277566607306513586L;

		public abstract void fromXML(String xml);

	}

	public static class BaseResponse extends Response {

		private static final long serialVersionUID = 609906079662414433L;

		public static class ErrorModel{
			@SerializedName("Error")
			String errorMessage;
		}

		private ErrorModel errorModel;
		
		@Override
		public void fromXML(String xml) {
			sGsonXml.fromXml(xml, ErrorModel.class);
		}

		public boolean hasError() {
			return errorModel != null && errorModel.errorMessage != null;
		}

		public String getErrorMessage() {
			return errorModel == null ? "" : errorModel.errorMessage;
		}
	}

	public static class GetGamesResponse extends BaseResponse{

		private static final long serialVersionUID = 2774048380287792954L;

		public static class GamesModel{
			@SerializedName("Games")
			public List<GameModel> games;
		}
		
		public static class GameModel{
			@SerializedName("Id")
			public long id;
			
			@SerializedName("FirstTeam")
			public FirstTeamModel firstTeam;
			
			@SerializedName("SecondTeam")
			public FirstTeamModel secondTeam;
		}
		
		public static class FirstTeamModel{
			@SerializedName("FirstTeamId")
			public long id;
			
			@SerializedName("Name")
			public String name;
			
			@SerializedName("Scores")
			public String scores;
			
			@SerializedName("Logo")
			public String logo;
		}
		
		public static class SecondTeamModel{
			@SerializedName("SecondTeamId")
			public long id;
			
			@SerializedName("Name")
			public String name;
			
			@SerializedName("Scores")
			public String scores;
			
			@SerializedName("Logo")
			public String logo;
		}
		
		public GamesModel gamesModel;

		@Override
		public void fromXML(String xml) {
			super.fromXML(xml);
			gamesModel = sGsonXml.fromXml(xml, GamesModel.class);
		}
		
	}
	
	private static GsonXml sGsonXml;
	static {
		XmlParserCreator parserCreator = new XmlParserCreator() {
			@Override
			public XmlPullParser createParser() {
				try {
					return XmlPullParserFactory.newInstance().newPullParser();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		};

		sGsonXml = new GsonXmlBuilder()
				.setXmlParserCreator(parserCreator)
				.create();

	}
}