package net.fifthfloorstudio.heroclixrules.plus.utils;

import net.fifthfloorstudio.heroclixrules.plus.HeroclixRulesPlus;
import net.fifthfloorstudio.heroclixrules.plus.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;

public class StringUtils {

	/*
	 * Parse a text and insert corresponding images
	 */
	public static SpannableStringBuilder parseText(Context context, String rule) {
		boolean images = context.getSharedPreferences(
				HeroclixRulesPlus.PREFS_NAME, Context.MODE_PRIVATE).getBoolean(
				HeroclixRulesPlus.PREFS_TOGGLE_IMAGES, false);
		if (images) {
			return parseTextAndInsertImages(context, rule);
		} else {
			return parseTextAndInsertText(context, rule);
		}
	}

	private static SpannableStringBuilder parseTextAndInsertText(
			Context context, String rule) {
		String[] tmp = rule.split("[#]");
		SpannableStringBuilder builder = new SpannableStringBuilder();
		builder.append(tmp[0]);
		
		for (int i = 1; i < tmp.length; i++) {
			String text = null;
			if (tmp[i].equals("speed")) {
				text = tmp[i];
			} else if (tmp[i].equals("attack")) {
				text = tmp[i];
			} else if (tmp[i].equals("defense")) {
				text = tmp[i];
			} else if (tmp[i].equals("damage")) {
				text = tmp[i];
			} else if (tmp[i].equals("swim")) {
				text = tmp[i];
			} else if (tmp[i].equals("sharpshooter")) {
				text = tmp[i];
			} else if (tmp[i].equals("indomitable")) {
				text = tmp[i];
			} else if (tmp[i].equals("flight") || tmp[i].equals("flying")) {
				text = "fligt";
			} else if (tmp[i].equals("duo") || tmp[i].equals("duoatc")) {
				text = "duo attack";
			} else if (tmp[i].equals("giant")) {
				text = tmp[i];
			} else if (tmp[i].equals("colossus")) {
				text = tmp[i];
			} else if (tmp[i].equals("transSwim")) {
				text = "transporter (doplhine)";
			} else if (tmp[i].equals("transFlight")) {
				text = "transpoter (wing)";
			} else if (tmp[i].equals("transSpeed")) {
				text = "transporter (boot)";
			} else if (tmp[i].equals("trait")) {
				text = tmp[i];
			} else if (tmp[i].equals("lightning")) {
				text = tmp[i];
			} else if (tmp[i].equals("3d")) {
				text = "3D";
			} else if (tmp[i].equals("tavengers")) {
				text = "Avengers";
			} else if (tmp[i].equals("lotrbook"))
				text = "Epic action";
			else if (tmp[i].equals("lotrm"))
				text = "'M'";
			else if (tmp[i].equals("lotrs"))
				text = "'S'";
			else if (tmp[i].equals("impmov"))
				text = "improved movement:";
			else if (tmp[i].equals("imptar"))
				text = "improved target:";
			else if (tmp[i].equals("ignet"))
				text = "ignores elevated terrain";
			else if (tmp[i].equals("ignht"))
				text = "ignores hindering terrain";
			else if (tmp[i].equals("ignbt"))
				text = "ignores blocking terrain";
			else if (tmp[i].equals("ignibt"))
				text = "ignores indoor blocking terrain";
			else if (tmp[i].equals("ignobt"))
				text = "ignores outdoor blocking terrain";
			else if (tmp[i].equals("igndmg"))
				text = "ignores blocking terrain and destroys blockign terrain as the character moves through it.";
			else if (tmp[i].equals("igndmgrng"))
				text = "ignores blocking terrain. When a ranged combat attack resolves, any blocking terrain along its line of fire to the target is destroyed.";
			else if (tmp[i].equals("ignfc"))
				text = "ignores friendly characters";
			else if (tmp[i].equals("ignoc"))
				text = "ignores opposing characters";
			else if (tmp[i].equals("doubleo"))
				text = "May move through squares adjacent to opposing characters, but still needs to break away normally.";
			else if (tmp[i].equals("doubleoarng"))
				text = "May make a ranged combat attack targeting adjacent opposing characters.";
			else if (tmp[i].equals("doubleoarr"))
				text = "May move through squares adjacent to or occupoed by opposing characters, but still needs to break away normally.";
			else if (tmp[i].equals("doubleoarrrng"))
				text = "May make a ranged combat attack while adjacent to an opposing character.";
			else if (tmp[i].equals("tiny"))
				text = "tiny size";
			else if (tmp[i].equals("imp"))
				text = "improvement";
			else if (tmp[i].equals("chara"))
				text = "ignores characters";
			// else if (tmp[i].equals("frag"))
			// image = R.drawable.frag;
			// else if (tmp[i].equals("flashbang"))
			// image = R.drawable.flashbang;
			// else if (tmp[i].equals("plasma"))
			// image = R.drawable.plasma;
			// else if (tmp[i].equals("thermite"))
			// image = R.drawable.thermite;
			// else if (tmp[i].equals("smoke"))
			// image = R.drawable.smoke;
			// else if (tmp[i].equals("ink"))
			// image = R.drawable.ink;
			else if (tmp[i].equals("mindgem"))
				text = "Mind Gem";
			else if (tmp[i].equals("powergem"))
				text = "Power Gem";
			else if (tmp[i].equals("realitygem"))
				text = "Reality Gem";
			else if (tmp[i].equals("soulgem"))
				text = "Soul Gem";
			else if (tmp[i].equals("spacegem"))
				text = "Space Gem";
			else if (tmp[i].equals("timegem"))
				text = "Time Gem";
			else if (tmp[i].equals("fancy4"))
				text = "4";
			else if (tmp[i].equals("fancy3"))
				text = "3";
			else if (tmp[i].equals("fancy2"))
				text = "2";
			else if (tmp[i].equals("fancy1"))
				text = "1";
			else if (tmp[i].equals("utilityBelt"))
				text = "Utility Belt";
			else if (tmp[i].equals("paraBox"))
				text = "paraBox";
			else if (tmp[i].equals("sos"))
				text = "costume slot";

			if (text != null) {
				if (i == 1 && text.matches("improved (movement|target):")) {
					tmp[2] = " "; // overrides the next comma
				}
				
				if (i == tmp.length - 2 && tmp.length > 5) {
					builder.append("and ");
				}
				
				builder.append(text);
			} else {
				builder.append(tmp[i]);
			}
		}
		
		return builder;
	}

	private static SpannableStringBuilder parseTextAndInsertImages(
			Context context, String rule) {
		String[] tmp = rule.split("[#]");
		SpannableStringBuilder builder = new SpannableStringBuilder();
		builder.append(tmp[0]);
		int lengthOfPart = builder.length();
		builder.append(" ");
		for (int i = 1; i < tmp.length; i++) {
			int image = -1;
			Drawable d = null;
			if (tmp[i].equals("speed")) {
				image = R.drawable.speed;
			} else if (tmp[i].equals("attack")) {
				image = R.drawable.attack;
			} else if (tmp[i].equals("defense")) {
				image = R.drawable.defense;
			} else if (tmp[i].equals("damage")) {
				image = R.drawable.damage;
			} else if (tmp[i].equals("swim")) {
				image = R.drawable.swim;
			} else if (tmp[i].equals("sharpshooter")) {
				image = R.drawable.sharpshooter;
			} else if (tmp[i].equals("indomitable")) {
				image = R.drawable.indomitable;
			} else if (tmp[i].equals("flight") || tmp[i].equals("flying")) {
				image = R.drawable.flight;
			} else if (tmp[i].equals("duo") || tmp[i].equals("duoatc")) {
				image = R.drawable.duo;
			} else if (tmp[i].equals("giant")) {
				image = R.drawable.giant;
			} else if (tmp[i].equals("colossus")) {
				image = R.drawable.colossus;
			} else if (tmp[i].equals("transSwim")) {
				image = R.drawable.transswim;
			} else if (tmp[i].equals("transFlight")) {
				image = R.drawable.transflight;
			} else if (tmp[i].equals("transSpeed")) {
				image = R.drawable.transspeed;
			} else if (tmp[i].equals("trait")) {
				image = R.drawable.trait;
			} else if (tmp[i].equals("lightning")) {
				image = R.drawable.bolt;
			} else if (tmp[i].equals("3d")) {
				image = R.drawable.threed;
			} else if (tmp[i].equals("tavengers")) {
				image = R.drawable.taavengers;
			} else if (tmp[i].equals("lotrbook"))
				image = R.drawable.lotrbook;
			else if (tmp[i].equals("lotrm"))
				image = R.drawable.lotrm;
			else if (tmp[i].equals("lotrs"))
				image = R.drawable.lotrs;
			else if (tmp[i].equals("impmov"))
				image = R.drawable.impmov;
			else if (tmp[i].equals("imptar"))
				image = R.drawable.imptar;
			else if (tmp[i].equals("ignet"))
				image = R.drawable.ignet;
			else if (tmp[i].equals("ignht"))
				image = R.drawable.ignht;
			else if (tmp[i].equals("ignbt"))
				image = R.drawable.ignbt;
			else if (tmp[i].equals("ignibt"))
				image = R.drawable.ignibt;
			else if (tmp[i].equals("ignobt"))
				image = R.drawable.ignobt;
			else if (tmp[i].equals("igndmg"))
				image = R.drawable.igndmg;
			else if (tmp[i].equals("ignfc"))
				image = R.drawable.ignfc;
			else if (tmp[i].equals("ignoc"))
				image = R.drawable.ignoc;
			else if (tmp[i].equals("doubleo"))
				image = R.drawable.doubleo;
			else if (tmp[i].equals("doubleoarr"))
				image = R.drawable.doubleoarr;
			else if (tmp[i].equals("tiny"))
				image = R.drawable.tiny;
			else if (tmp[i].equals("imp"))
				image = R.drawable.imp;
			else if (tmp[i].equals("chara"))
				image = R.drawable.chara;
			// else if (tmp[i].equals("frag"))
			// image = R.drawable.frag;
			// else if (tmp[i].equals("flashbang"))
			// image = R.drawable.flashbang;
			// else if (tmp[i].equals("plasma"))
			// image = R.drawable.plasma;
			// else if (tmp[i].equals("thermite"))
			// image = R.drawable.thermite;
			// else if (tmp[i].equals("smoke"))
			// image = R.drawable.smoke;
			// else if (tmp[i].equals("ink"))
			// image = R.drawable.ink;
			else if (tmp[i].equals("mindgem"))
				image = R.drawable.mindgem;
			else if (tmp[i].equals("powergem"))
				image = R.drawable.powergem;
			else if (tmp[i].equals("realitygem"))
				image = R.drawable.realitygem;
			else if (tmp[i].equals("soulgem"))
				image = R.drawable.soulgem;
			else if (tmp[i].equals("spacegem"))
				image = R.drawable.spacegem;
			else if (tmp[i].equals("timegem"))
				image = R.drawable.timegem;
			else if (tmp[i].equals("fancy4"))
				image = R.drawable.fancy4;
			else if (tmp[i].equals("fancy3"))
				image = R.drawable.fancy3;
			else if (tmp[i].equals("fancy2"))
				image = R.drawable.fancy2;
			else if (tmp[i].equals("fancy1"))
				image = R.drawable.fancy1;
			else if (tmp[i].equals("utilityBelt"))
				image = R.drawable.utilitybelt;
			else if (tmp[i].equals("paraBox"))
				image = R.drawable.parabox;
			else if (tmp[i].equals("sos"))
				image = R.drawable.sos;

			if (image != -1) {
				d = context.getResources().getDrawable(image);
				if (d == null) {
					builder.append(tmp[i]);
					builder.append(" ");
					lengthOfPart += tmp[i].length() + 1;
				} else {
					d.setBounds(0, 0, 24, 24); // <---- Very important otherwise
												// your image won't appear
					ImageSpan myImage = new ImageSpan(d);
					builder.setSpan(myImage, lengthOfPart, lengthOfPart + 1,
							Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					builder.append("");
				}
			} else {
				builder.append(tmp[i]);
				builder.append(" ");
				lengthOfPart += tmp[i].length() + 1;
			}
		}
		return builder;
	}
}
