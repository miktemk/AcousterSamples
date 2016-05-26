package org.acouster.eyefitness.bubbles;

import org.acouster.context.ContextBitmapFragment;
import org.acouster.eyefitness.Constants;
import org.acouster.graphics.anim.BubblePopVisual;
import org.acouster.logic.Sprite2D;

public class BubblePopVisual_btnGo extends BubblePopVisual
{
	public BubblePopVisual_btnGo(Sprite2D sprite) {
		super(sprite,
				Constants.Filenames.btnGo,
				Constants.Filenames.btnGo_cutoff,
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
//		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  367, 273, 64, 64), local_glob_g6, local_shadow_g6)); // 0
		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  353, 173, 99, 99), local_glob_g5, local_shadow_g5)); // 1
		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  189, 173, 162, 162), local_glob_g1, local_shadow_g1)); // 2
		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  106, 256, 82, 82), local_glob_g2, local_shadow_g2)); // 3
		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  106, 173, 82, 82), local_glob_g2, local_shadow_g2)); // 4
//		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  34, 273, 64, 64), local_glob_g6, local_shadow_g6)); // 5
		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  6, 173, 99, 99), local_glob_g5, local_shadow_g5)); // 6
		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  417, 117, 54, 54), local_glob_g3, local_shadow_g3)); // 7
		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  417, 61, 54, 54), local_glob_g3, local_shadow_g3)); // 8
//		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  417, 6, 54, 54), local_glob_g3, local_shadow_g3)); // 9
//		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  335, 106, 64, 64), local_glob_g6, local_shadow_g6)); // 10
		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  317, 6, 99, 99), local_glob_g5, local_shadow_g5)); // 11
		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  206, 60, 110, 110), local_glob_g4, local_shadow_g4)); // 12
		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  261, 6, 54, 54), local_glob_g3, local_shadow_g3)); // 13
		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  206, 6, 54, 54), local_glob_g3, local_shadow_g3)); // 14
//		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  106, 69, 99, 99), local_glob_g5, local_shadow_g5)); // 15
		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  117, 6, 63, 63), local_glob_g6, local_shadow_g6)); // 16
		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  27, 106, 64, 64), local_glob_g6, local_shadow_g6)); // 17
		bubbles.add(new BubbleStruct(new ContextBitmapFragment(bmpPieces,  6, 6, 99, 99), local_glob_g5, local_shadow_g5)); // 18

	}



}
