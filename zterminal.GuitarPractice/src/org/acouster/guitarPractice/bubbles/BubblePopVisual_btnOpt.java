package org.acouster.guitarPractice.bubbles;

import org.acouster.context.ContextBitmapFragment;
import org.acouster.graphics.anim.BubblePopVisual;
import org.acouster.logic.Sprite2D;
import org.acouster.guitarPractice.Constants;

public class BubblePopVisual_btnOpt extends BubblePopVisual
{
	// TODO: do GIMP magic for this bebe
	public BubblePopVisual_btnOpt(Sprite2D sprite)
	{
		super(sprite,
				Constants.Filenames.btnOpt,
				Constants.Filenames.btnOpt_cutoff,
				Constants.Filenames.globs_btn_menu,
				Constants.Filenames.bubble_pop_red, 5);
	}

	/** Override this function with code from the bubble XML generator tool */

	@Override
	public void initBubbleStructs()
	{
		ContextBitmapFragment local_glob_g1 = new ContextBitmapFragment(bmpGlobs, 11, 11, 164, 164);
		ContextBitmapFragment local_glob_g3 = new ContextBitmapFragment(bmpGlobs, 369, 11, 56, 56);
		ContextBitmapFragment local_glob_g2 = new ContextBitmapFragment(bmpGlobs, 230, 11, 84, 84);
		ContextBitmapFragment local_glob_g5 = new ContextBitmapFragment(bmpGlobs, 647, 11, 101, 101);
		ContextBitmapFragment local_glob_g4 = new ContextBitmapFragment(bmpGlobs, 480, 11, 112, 112);
		ContextBitmapFragment local_glob_g6 = new ContextBitmapFragment(bmpGlobs, 803, 11, 67, 67);
		ContextBitmapFragment local_shadow_g5 = new ContextBitmapFragment(bmpGlobs, 645, 209, 118, 118);
		ContextBitmapFragment local_shadow_g4 = new ContextBitmapFragment(bmpGlobs, 478, 209, 129, 129);
		ContextBitmapFragment local_shadow_g6 = new ContextBitmapFragment(bmpGlobs, 801, 209, 84, 84);
		ContextBitmapFragment local_shadow_g1 = new ContextBitmapFragment(bmpGlobs, 9, 209, 181, 181);
		ContextBitmapFragment local_shadow_g3 = new ContextBitmapFragment(bmpGlobs, 367, 209, 73, 73);
		ContextBitmapFragment local_shadow_g2 = new ContextBitmapFragment(bmpGlobs, 228, 209, 101, 101);
//		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  381, 256, 82, 82), local_glob_g2, local_shadow_g2)); // 0
//		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  381, 173, 82, 82), local_glob_g2, local_shadow_g2)); // 1
		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  217, 173, 162, 162), local_glob_g1, local_shadow_g1)); // 2
//		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  128, 273, 64, 64), local_glob_g6, local_shadow_g6)); // 3
		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  117, 173, 99, 99), local_glob_g5, local_shadow_g5)); // 4
		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  61, 284, 55, 55), local_glob_g3, local_shadow_g3)); // 5
//		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  6, 284, 55, 55), local_glob_g3, local_shadow_g3)); // 6
		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  6, 173, 110, 110), local_glob_g4, local_shadow_g4)); // 7
		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  381, 89, 82, 82), local_glob_g2, local_shadow_g2)); // 8
//		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  381, 6, 82, 82), local_glob_g2, local_shadow_g2)); // 9
		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  269, 60, 110, 110), local_glob_g4, local_shadow_g4)); // 10
		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  324, 6, 54, 54), local_glob_g3, local_shadow_g3)); // 11
		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  269, 6, 54, 54), local_glob_g3, local_shadow_g3)); // 12
		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  173, 106, 64, 64), local_glob_g6, local_shadow_g6)); // 13
//		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  169, 6, 99, 99), local_glob_g5, local_shadow_g5)); // 14
		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  6, 6, 162, 162), local_glob_g1, local_shadow_g1)); // 15
	}


}
