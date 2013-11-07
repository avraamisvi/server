package enums;

public interface WarriorsConstants {
	
	/**
	 * In seconds TODO refact the time
	 */
	int SLOW_CREATION = 180;
	int MIDDLE_CREATION = 90;
	int QUICKLY_CREATION = 60;
	
//			==========================
//			Level 1
//			==========================
			int SWORDMAN = 1;
//				power: 5
//				life: 5
//				velocity: 5
//				valor-manutenção: 5
				
//			==========================
//			Level 2
//			==========================

			int SPEARMAN = 2;
//				power: 10
//				life: 15
//				velocity: 3
//				needs: level 2
//				valor-manutenção: 15
				
			int SLING = 3;
//				power: 15
//				life:  15
//				velocity: 2
//				needs: level 2
//				valor-manutenção: 15
				
//			==========================
//			Level 3
//			==========================

			int KNIGHT = 4;
//				power: 20
//				life: 30
//				velocity: 5
//				needs: level 3, codigo conduta
//				valor-manutenção: 35
				
			int ARCHER = 5;
//				power: 15
//				life: 25
//				velocity: 4
//				needs: level 3, código conduta
//				valor-manutenção: 25

			int DOCTOR = 6;
//				power: 5
//				life: 25
//				velocity: 1
//				needs: level 3, código conduta, medicine
//				valor-manutenção: 60
					
//			==========================
//			Level 4
//			==========================	

			int CATAPULT = 7;
//				power: 45
//				life: 15
//				velocity: 2
//				needs: level 4, research pólvora, research machinery
//				valor-manutenção: 45

//			==========================
//			Level 5
//			==========================	

			int RIFLEMAN = 8;
//				power: 35
//				life: 35
//				velocity: 5
//				needs: level 5, research código conduta, research pólvora
//				valor-manutenção: 50

			int CANNON = 9;
//				power: 50
//				life: 25
//				velocity: 2
//				needs: level 5, research pólvora, research machinery
//				valor-manutenção: 100
			
			int BOAT = 10;
			int CARAVEL = 11;
			int SHIP = 12;			
			int AIRSHIP = 13;			
			
			
			int LEVEL_CAPTAIN = 15;//MAX
			int LEVEL_SARGEANT = 10;
			int LEVEL_CABO = 5;
			
/**
 * FORMATION			
 */

//					==========================
//					War formation 0
//					==========================		

					int TRIANGLE = 1;
//					    [] prot-0 atk-4
//					[] [] [] [] prot-2 atk-1

					int PARALLEL = 2;

//					[] [] [] [] [] prot-0 atk-5


//					==========================
//					War formation 2
//					==========================		

					int CROSS = 3;

//					    [] prot-0 atk-4
//					 [] [] [] prot-2 atk-3
//						[] prot-4 atk-0

					int QUARTER = 4;

//					[] [] prot-2 atk-3
//					[] [] prot-4 atk-2

					int TRIANGLE2 = 5; 

//					[] [] [] [] prot-0 atk-4
//					    [] prot-4 atk-0
					    
					int TRIANGLE3 = 6;

//					[]      [] prot-0 atk-2
//					  []  [] prot-1 atk-1
//					    []  prot-3 atk-0  
					    
					int TRIANGLE4 = 7;
//					    [] prot-0 atk-2
//					  []  [] prot-2 atk-1
//					[]      [] prot-4 atk-0			
}
