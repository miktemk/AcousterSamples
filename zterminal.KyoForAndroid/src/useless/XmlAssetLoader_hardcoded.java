package useless;
//
//
//// This file was autogenerated by the Acouster Organization
//
//package org.acouster.kyo;
//
//import org.acouster.IFunc;
//import org.acouster.context.ContextBitmap;
//import org.acouster.kyo.xml.*;
//import org.acouster.xml.olddd.GraphImage.*;
//import org.acouster.xml.olddd.GraphLogic.*;
//
//
//public class XmlAssetLoader_hardcoded
//{
//	public static Avatar LoadAndCompileAvatar(String filename, IFunc<String, ContextBitmap> id2image)
//	{
//		Avatar avatar = new Avatar();
//    
//        if (filename == "sample")
//        {
//            FsmGraph logic = new FsmGraph("NodeC");
//            ImageGraph visuals = new ImageGraph();
//            CameraSchedule schedule = new CameraSchedule();
//            //Logic
//			    FsmNode node0 = new FsmNode("NodeC");
//				    FsmEdge edge0 = new FsmEdge("L");
//					    edge0.addBranch(new FsmBranch("node_visit", 3, "L", "NodeC"));
//					    edge0.addBranch(new FsmBranch("prob", 1, "LG", "NodeCNG"));
//				    node0.addEdge(edge0);
//				    FsmEdge edge1 = new FsmEdge("R");
//					    edge1.addBranch(new FsmBranch("node_visit", 3, "R", "NodeC"));
//					    edge1.addBranch(new FsmBranch("prob", 1, "RG", "NodeCNG"));
//				    node0.addEdge(edge1);
//			    logic.addNode(node0);
//			    FsmNode node1 = new FsmNode("NodeCNG");
//				    FsmEdge edge2 = new FsmEdge("L");
//					    edge2.addBranch(new FsmBranch("node_visit", 4, "LNG", "NodeCNG"));
//					    edge2.addBranch(new FsmBranch("prob", 1, "T", "NodeT"));
//				    node1.addEdge(edge2);
//				    FsmEdge edge3 = new FsmEdge("R");
//					    edge3.addBranch(new FsmBranch("node_visit", 4, "RNG", "NodeCNG"));
//					    edge3.addBranch(new FsmBranch("prob", 1, "T", "NodeT"));
//				    node1.addEdge(edge3);
//			    logic.addNode(node1);
//			    FsmNode node2 = new FsmNode("NodeT");
//			    logic.addNode(node2);
//            //Visuals
//                visuals.addNode(new ImageNode("L1", "L", "node", 0.1, true));
//                visuals.addNode(new ImageNode("L2", "", "node", 0.85, false));
//                visuals.addNode(new ImageNode("C1", "C", "node", 0, false));
//                visuals.addNode(new ImageNode("R1", "R", "node", 0.1, true));
//                visuals.addNode(new ImageNode("R2", "", "node", 0.85, false));
//                visuals.addNode(new ImageNode("", "C", "linkto", 0, false));
//                visuals.addNode(new ImageNode("GL1", "LG", "node", 0.1, true));
//                visuals.addNode(new ImageNode("GL2", "", "node", 0.85, false));
//                visuals.addNode(new ImageNode("C2", "C2", "node", 0, false));
//                visuals.addNode(new ImageNode("GR1", "RG", "node", 0.1, true));
//                visuals.addNode(new ImageNode("GR2", "", "node", 0.85, false));
//                visuals.addNode(new ImageNode("", "C2", "linkto", 0, false));
//                visuals.addNode(new ImageNode("NGL1", "LNG", "node", 0.85, true));
//                visuals.addNode(new ImageNode("", "C2", "linkto", 0, false));
//                visuals.addNode(new ImageNode("NGR1", "RNG", "node", 0.85, true));
//                visuals.addNode(new ImageNode("", "C2", "linkto", 0, false));
//                visuals.addNode(new ImageNode("T1", "T", "node", 0.1, true));
//                visuals.addNode(new ImageNode("T2", "", "node", 0.1, false));
//                visuals.addNode(new ImageNode("T3", "", "node", 0.1, false));
//                visuals.addNode(new ImageNode("T4", "", "node", 0, false));
//            //Schedule
//                schedule.addNode(new SchedulePicture("C1", "stage 1 center"));
//                schedule.addNode(new SchedulePicture("L1", "stage 1 left 45"));
//                schedule.addNode(new SchedulePicture("L2", "stage 1 left 90"));
//                schedule.addNode(new SchedulePicture("R1", "stage 1 right 45"));
//                schedule.addNode(new SchedulePicture("R2", "stage 1 right 90"));
//                schedule.addNode(new SchedulePicture("GL1", "glasses fly off left 1"));
//                schedule.addNode(new SchedulePicture("GL2", "glasses fly off left 2"));
//                schedule.addNode(new SchedulePicture("GR1", "glasses fly off right 1"));
//                schedule.addNode(new SchedulePicture("GR2", "glasses fly off right 2"));
//                schedule.addNode(new SchedulePicture("C2", "no glasses center"));
//                schedule.addNode(new SchedulePicture("NGL1", "no glasses left"));
//                schedule.addNode(new SchedulePicture("NGR1", "no glasses right"));
//                schedule.addNode(new SchedulePicture("T1", "toppled frame 1"));
//                schedule.addNode(new SchedulePicture("T2", "toppled frame 2"));
//                schedule.addNode(new SchedulePicture("T3", "toppled frame 3"));
//                schedule.addNode(new SchedulePicture("T4", "toppled frame 4"));
//            avatar.setLogic(logic);
//            avatar.setVisuals(visuals);
//            avatar.setSchedule(schedule);
//        }
//
//    
//        compileAvatar(avatar, id2image);
//
//		return avatar;
//	}
//
//	
//    //============== privates ==============
//
//    
//    private static void compileAvatar(Avatar avatar, IFunc<String, ContextBitmap> id2image)
//	{
//		avatar.compile(id2image);
//	}
//}