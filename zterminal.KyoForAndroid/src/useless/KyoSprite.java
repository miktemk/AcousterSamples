package useless;
//package acouster.game.kyo.logic;
//
//import acouster.game.basis.GameEvent;
//import acouster.game.game2D.logic.Sprite2D;
//
//public class SlapMeSprite extends Sprite2D
//{
//	public SlapMeSprite(String name, double x, double y, String currentAnimation) {
//		super(name, x, y, currentAnimation);
//		// TODO Auto-generated constructor stub
//	}
//
//	private int slapCount = 0;
//	
//	@Override
//	public void handleActionMessage(GameEvent message)
//	{
//		// TODO: ........ state machine????????
//		if (slapCount < 3)
//			enqueueViewMessage(message.getBody());
//		else if (slapCount < 4)
//			enqueueViewMessage(message.getBody() + "G");
//		else if (slapCount < 8)
//			enqueueViewMessage(message.getBody() + "NG");
//		else
//			enqueueViewMessage("T");
//		slapCount++;
//	}
//	
//}
