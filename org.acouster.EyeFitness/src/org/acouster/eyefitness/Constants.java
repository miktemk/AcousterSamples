package org.acouster.eyefitness;

public final class Constants
{
    public static final class Sounds {
    	public static final String POOF = "POOF";
    	public static final String EEEEK = "EEEEK";
    	public static final String CONGRADS = "CONGRADS";
		public static final String DRUMS = "drums";
    }
    public static final class Values {
    	public static final int WIGGLING_FRAMES = 4;
    	public static final int stonePressYOffset = 5;
    	/** set to 5 for debug / 1 for release */
        public static final double ANT_SPEED_REDUCTION_FACTOR = 1;
        /** set to 0 for debug / 50 for release (use this for margin on the border along which ants are targeted to) */
        public static final double ESCAPE_EDGE_OF_WINDOW_MARGIN = 50;
        public static final double ANT_SPEED_SPEED_VARIATION_FACTOR_MIN = 0.8;
        public static final double ANT_SPEED_SPEED_VARIATION_FACTOR_MAX = 1.2;
		public static final int STONE_MARGIN_PIX = 20;
    }
    public static final class Filenames {
    	public static final String btnGo = "btn_go.png";
    	public static final String btnGo_cutoff = "btn_go_cutoff.png";
    	public static final String btnGoDown = "btn_go_down.png";
    	public static final String btnOpt = "btn_opt.png";
    	public static final String btnOpt_cutoff = "btn_go_cutoff.png";
    	public static final String btnOptDown = "btn_opt_down.png";
    	public static final String bg1 = "bg.jpg";
//    	public static final String bg2 = "bg2.jpg";
//    	public static final String bg3 = "bg3.jpg";
//    	public static final String bg4 = "bg4.jpg";
    	public static final String globs_btn_menu = "globs_btn_menu.png";
    	public static final String globs_btn_diff = "globs_btn_diff.png";
    	public static final String bubble_pop_red = "bubble_pop_red.png";
    	
    	public static final String pupil1 = "pupil1.png";
    	public static final String zorro1 = "zorro1.png";
    	public static final String zorro1_closed = "zorro1_closed.png";
    }
}

