package com.wordart.gen.entities;

import java.util.EnumSet;
import java.util.UUID;


public class Job implements Cloneable {


	/**
	 * Supported output formats
	 */
	public static enum Format {
		PNG, JPEG, TIFF, ASCII
	}

	/**
	 * Font effects for rendered output
	 */
	public static enum Effect {
		SHADOW_HARD, SHADOW_REFLECT, GRADIENT, OUTLINE
	}

	/**
	 * Supported colours
	 */
	public static enum Colour {
		RED, GREEN, BLUE, BLACK, GREY, WHITE
	}

	/**
	 * Support fonts for word art
	 */
	public static enum Font {
		FREE_MONO("FreeMono");

		Font(String fontName) {
			this.fontName = fontName;
		}

		private String fontName;

		public String getFontName() {
			return fontName;
		}

		public static Font fromValue(String text) {
			for (Font b : Font.values()) {
				if (String.valueOf(b.fontName).equals(text)) {
					return b;
				}
			}
			throw new IllegalArgumentException("Unexpected value '" + text + "'");
		}
	}

	public static enum Status {
		ACCEPTED, RUNNING, GENERATED, FAILED, DELETED
	}

	private Format format;
	private String text;
	private EnumSet<Effect> effects;
	private Colour fontColour;
	private Font font;
	private UUID id;
	private Status status;

	/**
	 * No arg constructor
	 */
	public Job() {
	}

	
	/**
	 * Create a new job specification
	 * 
	 * @param id
	 *            auto generated id of job
	 * @param format
	 *            file output format for the result.
	 * @param text
	 *            the text that is to be rendered
	 * @param effects
	 *            the set of effects that you want applied to your output text
	 * @param fontColour
	 *            the colour to use when rendering the font
	 * @param font
	 *            the font face to use when rendering the font
	 * @param status
	 *            status of job
	 */
	public Job(UUID id, Format format, String text, EnumSet<Effect> effects, Colour fontColour, Font font,
			Status status) {
		this.id = id;
		this.format = format;
		this.text = text;
		this.effects = effects;
		this.fontColour = fontColour;
		this.font = font;
		this.status = status;
	}

	public UUID getId() {
		return id;
	}

	public Format getFormat() {
		return format;
	}

	public String getText() {
		return text;
	}

	public EnumSet<Effect> getEffects() {
		return effects;
	}

	public Colour getFontColour() {
		return fontColour;
	}

	public Font getFont() {
		return font;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "Job [format=" + format + ", text=" + text + ", effects=" + effects + ", fontColour=" + fontColour
				+ ", font=" + font + ", id=" + id + ", status=" + status + "]";
	}



}
