package utilz;

import java.awt.event.KeyEvent;

import main.Game;

public class Constants {

    public static final float GRAVITY = 0.04f*Game.SCALE;
    public static final int ANI_SPEED=25;

    public static class Dialogue {
        public static final int QUESTION = 0;
        public static final int EXCLAMATION = 1;

        public static final int DIALOGUE_WIDTH = (int) (14 * Game.SCALE);
        public static final int DIALOGUE_HEIGHT = (int) (12 * Game.SCALE);

        public static int GetSpriteAmount(int type) {
            switch (type) {
                case QUESTION, EXCLAMATION:
                    return 5;
            }

            return 0;
        }
    }
    
    public static final String getIntroText(int index) {
    	switch(index) {
    	case 0:
    		return "Chào mừng bạn đến với Black Myth Wukong";
    	case 1:
    		return "Thế giới đang chìm trong hỗn loạn,nơi chỉ\nkẻ mạnh nhất mới có thể đứng trên đỉnh cao\nvinh quang.";
    	case 2:
    		return "Bạn đã được định mệnh lựa chọn để hóa thân\nthành Tôn Ngộ Không – vị vua khỉ huyền thoại.\nVới cây gậy Như Ý uy lực trong tay và trí\ntuệ sắc bén, bạn có khả năng đánh bại và\ntiêu diệt kẻ thù nhanh chóng.";
    	case 3:
    		return "Nhưng hãy nhớ: con đường đến ngôi vị bá chủ\nkhông hề dễ dàng. Kẻ thù thì đông, hiểm nguy\nluôn rình rập, và bạn… chỉ có một mình. Liệu\nbạn có đủ bản lĩnh để đối đầu?";
    	case 4:
    		return "Hãy tận dụng tất cả kỹ năng và sự thông minh\ncủa mình, chinh phục những thử thách khắc\nnghiệt, đánh bại thế lực bóng tối và khắc tên\nmình vào huyền thoại. Vận mệnh của một bá\nchủ giờ đây nằm trong tay bạn!";
    	case 5:
    		return "Để sử dụng kỹ năng hiệu quả, bạn hãy nhấn\nnút R để ôn luyện lại combo chiêu nhé!";
    	case 6:
    		return "CHÚC BẠN SỐNG DAI THÀNH HUYỀN THOẠI!";
    	default:
    		return "Chúc may mắn";
    			
    	}
    }
    

    public static class Curve{
        public static final int STICK_WIDTH_DEFAULT = 40;
        public static final int STICK_HEIGHT_DEFAULT = 4;

        public static final int STICK_WIDTH = (int)(Game.SCALE*STICK_WIDTH_DEFAULT);
        public static final int STICK_HEIGHT = (int)(Game.SCALE*STICK_HEIGHT_DEFAULT);
        public static final float SPEED = 1.75f*Game.SCALE;



    }
    
    public static class Projectiles{
		public static final int CANNON_BALL_DEFAULT_WIDTH = 15;
		public static final int CANNON_BALL_DEFAULT_HEIGHT = 15;
		
		public static final int CANNON_BALL_WIDTH = (int)(Game.SCALE * CANNON_BALL_DEFAULT_WIDTH);
		public static final int CANNON_BALL_HEIGHT = (int)(Game.SCALE * CANNON_BALL_DEFAULT_HEIGHT);
		public static final float SPEED = 0.75f * Game.SCALE;
	}

    public static class Arrows{
        public static final int ARROW_DEFAULT_WIDTH = 30;
        public static final int ARROW_DEFAULT_HEIGHT = 10;

        public static final int ARROW_WIDTH = (int)(Game.SCALE*ARROW_DEFAULT_WIDTH);
        public static final int ARROW_HEIGHT = (int)(Game.SCALE*ARROW_DEFAULT_HEIGHT);
        public static final float SPEED = 0.4f*Game.SCALE;



    }

    public static class ObjectsConstants {
        public static final int RED_POTION = 0;
        public static final int BLUE_POTION = 1;
        public static final int BARREL = 2;
        public static final int BOX = 3;

        public static final int FLYWUKONG = 15;

        public static final int TRAP1 = 4;
        public static final int CHEST = 5;
        public static final int SCROLL = 6;
        public static final int SWORD = 7;
        public static final int CANNON_LEFT = 8;
        public static final int CANNON_RIGHT = 9;
        public static final int ARROW_TRAP = 10;
        public static final int TRAP2_LEFT = 11;
        public static final int TRAP2_RIGHT = 12;
        public static final int PEACH = 13;
        public static final int FLAG = 14;
        public static final int EXPLOSION = 15;
        public static final int BANANA = 16;


        public static final int RED_POTION_VALUE = 15;
        public static final int BLUE_POTION_VALUE = 100;
        public static final int TRAP1_VALUE = 20;
        public static final int SWORD_VALUE = 20;
        public static final int PEACH_VALUE = 100;
        public static final int EXPLOSION_VALUE = 30;
        
        public static final int EXPLOSION_WIDTH_DEFAULT = 64;
        public static final int EXPLOSION_HEIGHT_DEFAULT = 64;
        public static final int EXPLOSION_WIDTH = (int) (EXPLOSION_WIDTH_DEFAULT * Game.SCALE);
        public static final int EXPLOSION_HEIGHT = (int) (EXPLOSION_HEIGHT_DEFAULT * Game.SCALE);
        
        public static final int PEACH_WIDTH_DEFAULT = 15;
        public static final int PEACH_HEIGHT_DEFAULT = 15;
        public static final int PEACH_WIDTH = (int) (PEACH_WIDTH_DEFAULT * Game.SCALE);
        public static final int PEACH_HEIGHT = (int) (PEACH_HEIGHT_DEFAULT * Game.SCALE);
        
        public static final int BANANA_WIDTH_DEFAULT = 15;
        public static final int BANANA_HEIGHT_DEFAULT = 15;
        public static final int BANANA_WIDTH = (int) (BANANA_WIDTH_DEFAULT * Game.SCALE);
        public static final int BANANA_HEIGHT = (int) (BANANA_HEIGHT_DEFAULT * Game.SCALE);
        
        public static final int FLAG_WIDTH_DEFAULT = 35;
        public static final int FLAG_HEIGHT_DEFAULT = 35;
        public static final int FLAG_WIDTH = (int) (FLAG_WIDTH_DEFAULT * Game.SCALE);
        public static final int FLAG_HEIGHT = (int) (FLAG_HEIGHT_DEFAULT * Game.SCALE);
        
        public static final int TRAP2_WIDTH_DEFAULT = 90;
        public static final int TRAP2_HEIGHT_DEFAULT = 30;
        public static final int TRAP2_WIDTH = (int) (TRAP2_WIDTH_DEFAULT * Game.SCALE);
        public static final int TRAP2_HEIGHT = (int) (TRAP2_HEIGHT_DEFAULT * Game.SCALE);
        
        public static final int ARROW_TRAP_WIDTH_DEFAULT = 90;
		public static final int ARROW_TRAP_HEIGHT_DEFAULT = 30;
		public static final int ARROW_TRAP_WIDTH = (int) (ARROW_TRAP_WIDTH_DEFAULT * Game.SCALE);
		public static final int ARROW_TRAP_HEIGHT = (int) (ARROW_TRAP_HEIGHT_DEFAULT * Game.SCALE);
        
        public static final int CANNON_WIDTH_DEFAULT = 40;
		public static final int CANNON_HEIGHT_DEFAULT = 26;
		public static final int CANNON_WIDTH = (int) (CANNON_WIDTH_DEFAULT * Game.SCALE);
		public static final int CANNON_HEIGHT = (int) (CANNON_HEIGHT_DEFAULT * Game.SCALE);

        public static final int CONTAINER_WIDTH_DEFAULT = 40;
        public static final int CONTAINER_HEIGHT_DEFAULT = 30;
        public static final int CONTAINER_WIDTH = (int) (CONTAINER_WIDTH_DEFAULT * Game.SCALE);
        public static final int CONTAINER_HEIGHT = (int) (CONTAINER_HEIGHT_DEFAULT * Game.SCALE);
        
        public static final int CHEST_WIDTH_DEFAULT = 48;
        public static final int CHEST_HEIGHT_DEFAULT = 48;
        public static final int CHEST_WIDTH = (int) (CHEST_WIDTH_DEFAULT * Game.SCALE);
        public static final int CHEST_HEIGHT = (int) (CHEST_HEIGHT_DEFAULT * Game.SCALE);
        
        public static final int SWORD_WIDTH_DEFAULT = 15;
        public static final int SWORD_HEIGHT_DEFAULT = 15;
        public static final int SWORD_WIDTH = (int) (SWORD_WIDTH_DEFAULT * Game.SCALE);
        public static final int SWORD_HEIGHT = (int) (SWORD_HEIGHT_DEFAULT * Game.SCALE);
        
        public static final int SCROLL_WIDTH_DEFAULT = 20;
        public static final int SCROLL_HEIGHT_DEFAULT = 20;
        public static final int SCROLL_WIDTH = (int) (SCROLL_WIDTH_DEFAULT * Game.SCALE);
        public static final int SCROLL_HEIGHT = (int) (SCROLL_HEIGHT_DEFAULT * Game.SCALE);
        
        public static final int TRAP1_WIDTH_DEFAULT = 32;
        public static final int TRAP1_HEIGHT_DEFAULT = 32;
        public static final int TRAP1_WIDTH = (int) (TRAP1_WIDTH_DEFAULT * Game.SCALE);
        public static final int TRAP1_HEIGHT = (int) (TRAP1_HEIGHT_DEFAULT * Game.SCALE);

        public static final int POTION_WIDTH_DEFAULT = 12;
        public static final int POTION_HEIGHT_DEFAULT = 16;
        public static final int POTION_WIDTH = (int) (POTION_WIDTH_DEFAULT * Game.SCALE);
        public static final int POTION_HEIGHT = (int) (POTION_HEIGHT_DEFAULT * Game.SCALE);

        public static int getSpriteAmount(int objType) {
            switch (objType) {
            	case EXPLOSION:
            		return 12;
            	case TRAP2_LEFT, TRAP2_RIGHT:
            		return 10;
            	case ARROW_TRAP:
            		return 15;
                case RED_POTION, BLUE_POTION, CANNON_LEFT, CANNON_RIGHT:
                    return 7;
                case BARREL, BOX:
                    return 8;
                case CHEST:
                	return 3;
            }

            return 1;
        }
    }
    public static class EnemyConstants {
    	
        public static final int CRABBY = 0;
        public static final int MINOTAUR = 1;
        public static final int SHARK = 2;        
        public static final int MONSTER_EYE1 = 3;
        public static final int MONSTER2 = 4;
        public static final int SPIDER = 5;
        public static final int BOSS1 = 7;
        public static final int BOSS5 = 11;
        
        public static final int TORO = 6;
        public static final int BOSS2 = 8;
        public static final int BOSS3 = 9;
        public static final int BOSS4 = 10;
        public static final int BOSSFINAL = 12;
       
        
        
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACK = 2;
        public static final int HIT = 3;
        public static final int DEAD = 4;
        public static final int ATTACK2 = 5;
        public static final int IDLE_2 = 6;
        
        
        
        public static final int	BOSSFINAL_WIDTH_DEFAULT = 64;
        public static final int BOSSFINAL_HEIGHT_DEFAULT = 40;
        public static final int BOSSFINAL_WIDTH = (int) (BOSSFINAL_WIDTH_DEFAULT * Game.SCALE);
        public static final int BOSSFINAL_HEIGHT = (int) (BOSSFINAL_HEIGHT_DEFAULT * Game.SCALE);
        public static final int BOSSFINAL_DRAWOFFSET_X = (int) (38 * Game.SCALE);
        public static final int BOSSFINAL_DRAWOFFSET_Y = (int) (15 * Game.SCALE);
        
        
        public static final int	BOSS5_WIDTH_DEFAULT = 96;
        public static final int BOSS5_HEIGHT_DEFAULT = 64;
        public static final int BOSS5_WIDTH = (int) (BOSS5_WIDTH_DEFAULT * Game.SCALE);
        public static final int BOSS5_HEIGHT = (int) (BOSS5_HEIGHT_DEFAULT * Game.SCALE);
        public static final int BOSS5_DRAWOFFSET_X = (int) (8 * Game.SCALE);
        public static final int BOSS5_DRAWOFFSET_Y = (int) (30 * Game.SCALE);
        
        public static final int	BOSS4_WIDTH_DEFAULT = 288;
        public static final int BOSS4_HEIGHT_DEFAULT = 160;
        public static final int BOSS4_WIDTH = (int) (BOSS4_WIDTH_DEFAULT * Game.SCALE);
        public static final int BOSS4_HEIGHT = (int) (BOSS4_HEIGHT_DEFAULT * Game.SCALE);
        public static final int BOSS4_DRAWOFFSET_X = (int) (108 * Game.SCALE);
        public static final int BOSS4_DRAWOFFSET_Y = (int) (54 * Game.SCALE);
        
        public static final int	BOSS3_WIDTH_DEFAULT = 128;
        public static final int BOSS3_HEIGHT_DEFAULT = 96;
        public static final int BOSS3_WIDTH = (int) (BOSS3_WIDTH_DEFAULT * Game.SCALE);
        public static final int BOSS3_HEIGHT = (int) (BOSS3_HEIGHT_DEFAULT * Game.SCALE);
        public static final int BOSS3_DRAWOFFSET_X = (int) (38 * Game.SCALE);
        public static final int BOSS3_DRAWOFFSET_Y = (int) (44 * Game.SCALE);
        
        public static final int	BOSS2_WIDTH_DEFAULT = 128;
        public static final int BOSS2_HEIGHT_DEFAULT = 96;
        public static final int BOSS2_WIDTH = (int) (BOSS2_WIDTH_DEFAULT * Game.SCALE);
        public static final int BOSS2_HEIGHT = (int) (BOSS2_HEIGHT_DEFAULT * Game.SCALE);
        public static final int BOSS2_DRAWOFFSET_X = (int) (41 * Game.SCALE);
        public static final int BOSS2_DRAWOFFSET_Y = (int) (24 * Game.SCALE);
        
        public static final int	SPIDER_WIDTH_DEFAULT = 32;
        public static final int SPIDER_HEIGHT_DEFAULT = 32;
        public static final int SPIDER_WIDTH = (int) (SPIDER_WIDTH_DEFAULT * Game.SCALE);
        public static final int SPIDER_HEIGHT = (int) (SPIDER_HEIGHT_DEFAULT * Game.SCALE);
        public static final int SPIDER_DRAWOFFSET_X = (int) (23 * Game.SCALE);
        public static final int SPIDER_DRAWOFFSET_Y = (int) (9 * Game.SCALE);
        
        public static final int	MON2_WIDTH_DEFAULT = 32;
        public static final int MON2_HEIGHT_DEFAULT = 32;
        public static final int MON2_WIDTH = (int) (MON2_WIDTH_DEFAULT * Game.SCALE);
        public static final int MON2_HEIGHT = (int) (MON2_HEIGHT_DEFAULT * Game.SCALE);
        public static final int MON2_DRAWOFFSET_X = (int) (2 * Game.SCALE);
        public static final int MON2_DRAWOFFSET_Y = (int) (9 * Game.SCALE);
        
        public static final int	MONEYE1_WIDTH_DEFAULT = 64;
        public static final int MONEYE1_HEIGHT_DEFAULT = 64;
        public static final int MONEYE1_WIDTH = (int) (MONEYE1_WIDTH_DEFAULT * Game.SCALE);
        public static final int MONEYE1_HEIGHT = (int) (MONEYE1_HEIGHT_DEFAULT * Game.SCALE);
        public static final int MONEYE1_DRAWOFFSET_X = (int) (23 * Game.SCALE);
        public static final int MONEYE1_DRAWOFFSET_Y = (int) (30 * Game.SCALE);
        
        public static final int	BOSS1_WIDTH_DEFAULT = 96;
        public static final int BOSS1_HEIGHT_DEFAULT = 64;
        public static final int BOSS1_WIDTH = (int) (BOSS1_WIDTH_DEFAULT * Game.SCALE);
        public static final int BOSS1_HEIGHT = (int) (BOSS1_HEIGHT_DEFAULT * Game.SCALE);
        public static final int BOSS1_DRAWOFFSET_X = (int) (34 * Game.SCALE);
        public static final int BOSS1_DRAWOFFSET_Y = (int) (16 * Game.SCALE);

        public static final int CRABBY_WIDTH_DEFAULT = 72;
        public static final int CRABBY_HEIGHT_DEFAULT = 32;
        public static final int CRABBY_WIDTH = (int) (CRABBY_WIDTH_DEFAULT * Game.SCALE);
        public static final int CRABBY_HEIGHT = (int) (CRABBY_HEIGHT_DEFAULT * Game.SCALE);
        public static final int CRABBY_DRAWOFFSET_X = (int) (26 * Game.SCALE);
        public static final int CRABBY_DRAWOFFSET_Y = (int) (1 * Game.SCALE);

        public static final int MINOTAUR_WIDTH_DEFAULT = 80;
        public static final int MINOTAUR_HEIGHT_DEFAULT = 80;
        public static final int MINOTAUR_WIDTH = (int) (MINOTAUR_WIDTH_DEFAULT * Game.SCALE);
        public static final int MINOTAUR_HEIGHT = (int) (MINOTAUR_HEIGHT_DEFAULT * Game.SCALE);
        public static final int MINOTAUR_DRAWOFFSET_X = (int) (-50 * Game.SCALE);
        public static final int MINOTAUR_DRAWOFFSET_Y = (int) (35* Game.SCALE);
        
        public static final int TORO_WIDTH_DEFAULT = 96;
        public static final int TORO_HEIGHT_DEFAULT = 96;
        public static final int TORO_WIDTH = (int) (TORO_WIDTH_DEFAULT * Game.SCALE);
        public static final int TORO_HEIGHT = (int) (TORO_HEIGHT_DEFAULT * Game.SCALE);
        public static final int TORO_DRAWOFFSET_X = (int) (32 * Game.SCALE);
        public static final int TORO_DRAWOFFSET_Y = (int) (32* Game.SCALE);

        public static final int SHARK_WIDTH_DEFAULT = 34;
        public static final int SHARK_HEIGHT_DEFAULT = 30;
        public static final int SHARK_WIDTH = (int) (SHARK_WIDTH_DEFAULT * Game.SCALE);
        public static final int SHARK_HEIGHT = (int) (SHARK_HEIGHT_DEFAULT * Game.SCALE);
        public static final int SHARK_DRAWOFFSET_X = (int) (8 * Game.SCALE);
        public static final int SHARK_DRAWOFFSET_Y = (int) (0 * Game.SCALE);

        public static int GetSpriteAmount(int enemy_type, int enemy_state) {
            switch (enemy_state) {

                case IDLE:
                    if (enemy_type == CRABBY)
                        return 9;
                    else if ( enemy_type == SHARK || enemy_type == BOSS5)
                        return 8;
                    else if(enemy_type == MINOTAUR)
                        return 9;
                    else if( enemy_type == TORO || enemy_type == SPIDER || enemy_type == BOSSFINAL)
                    	return 5;
                    else if( enemy_type == BOSS1 || enemy_type == BOSS4 )
                    	return 6;
                    else if(enemy_type == MONSTER_EYE1)
                    	return 15;
                    else if(enemy_type == MONSTER2 || enemy_type == BOSS3)
                    	return 4;
                    else if(enemy_type == BOSS2)
                    	return 7;
                    

                case RUNNING:
                	if(enemy_state == TORO || enemy_state == MONSTER2 || enemy_type == BOSS5)
                		return 8;
                	else if(enemy_type == MONSTER_EYE1 || enemy_type == BOSS4)
                    	return 12;
                	else if(enemy_type == SPIDER)
                    	return 5;
                	else if(enemy_type == BOSS2 || enemy_type == BOSS3)
                    	return 7;
                	else if(enemy_type == BOSSFINAL)
                		return 6;
                    return 6;
                case ATTACK:
                    if (enemy_type == SHARK || enemy_type == BOSS5)
                        return 8;
                    else if(enemy_type==MINOTAUR)
                        return 12;
                    else if (enemy_type == TORO)
                    	return 9;
                    else if( enemy_type == BOSS1 || enemy_type == BOSS2)
                    	return 6;
                    else if(enemy_type == MONSTER_EYE1)
                    	return 9;
                    else if(enemy_type == SPIDER || enemy_type == BOSS3)
                    	return 4;
                    else if(enemy_type == BOSS4)
                    	return 15;
                    else if(enemy_type == BOSSFINAL)
                    	return 3;
                    return 7;
                case HIT:
                    if(enemy_type==MINOTAUR || enemy_type == BOSS4)
                        return 5;
                    else if (enemy_type == TORO || enemy_type == BOSS1 || enemy_type == SPIDER || enemy_type == BOSS3 || enemy_type == BOSSFINAL)
                    	return 3;
                    else if(enemy_type == MONSTER_EYE1)
                    	return 6;
                    return 4;
                    
                case DEAD:
                    if(enemy_type==MINOTAUR)
                        return 23;
                    else if (enemy_type == TORO || enemy_type == MONSTER2 || enemy_type == BOSS2)
                    	return 6;
                    else if (enemy_type == BOSS1)
                    	return 4;
                    else if(enemy_type == MONSTER_EYE1 || enemy_type == SPIDER)
                    	return 9;
                    else if(enemy_type == BOSS3 || enemy_type == BOSSFINAL)
                    	return 7;
                    else if(enemy_type == BOSS4)
                    	return 19;
                    return 5;
                    
                case IDLE_2:
                	if(enemy_type ==TORO)
                		return 6;
                	else if(enemy_type == BOSSFINAL)
                		return 4;
                	return 0;
                	
                	
                case ATTACK2:
                if(enemy_type == TORO || enemy_type == BOSS3)
                	return 4;
                else if(enemy_type == BOSS2)
                	return 6;
                else if(enemy_type == BOSSFINAL)
                	return 8;
                return 0;
            }

            return 0;

        }

        public static int GetExperience(int enemy_type){
            switch (enemy_type) {
                case CRABBY:
                    return 10;
                case MINOTAUR:
                    return 55;
                case SHARK:
                    return 5;
                case MONSTER_EYE1:
                    return 20;
                case MONSTER2:
                    return 20;
                case SPIDER:
                    return 25;
                case BOSS1:
                    return 40;
                case BOSS5:
                    return 80;
                case TORO:  
                    return 35;
                case BOSS2:
                    return 50;
                case BOSS3:
                    return 60;
                case BOSS4:
                    return 65;
                case BOSSFINAL:
                    return 100;
                default:
                    return 20;
              }
        }

        public static int GetMaxHealth(int enemy_type) {
            switch (enemy_type) {
                    case CRABBY:
                        return 15;
                    case MINOTAUR:
                        return 105;
                    case SHARK:
                        return 10;
                    case MONSTER_EYE1:
                        return 35;
                    case MONSTER2:
                        return 45;
                    case SPIDER:
                        return 25;
                    case BOSS1:
                        return 80;
                    case BOSS5:
                        return 280;
                    case TORO:  
                        return 70;
                    case BOSS2:
                        return 100;
                    case BOSS3:
                        return 150;
                    case BOSS4:
                        return 200;
                    case BOSSFINAL:
                        return 300;
                    default:
                        return 20;
                  }
        }

        public static int GetEnemyDmg(int enemy_type) {
            switch (enemy_type) {
               case CRABBY:
                   return 5;
                case MINOTAUR:
                   return 20;
               case SHARK:
              	return 5;
                case TORO:
               	return 15;
                case BOSS1:
               	return 10;
                case BOSS2:
               		return 15;
                case BOSS3:
                	return 15;
                case BOSS4:
                	return 20;
                case BOSS5:
                	return 20;
                case MONSTER_EYE1:
                	return 10;
                case SPIDER :
                	return 5;
                case BOSSFINAL:
                	return 25;
                default:
                	return 0;
            }
        }
        
        
        public static int GetNumberMessageBoss(int enemy_type) {
        	switch(enemy_type) {
        	case TORO:
        		return 5;
        	case BOSS2:
        		return 5;
        	case BOSS3:
        		return 5;
        	case BOSS4:
        		return 6;
        	case BOSSFINAL:
        		return 11;
        	default:
        		return 3;
        	}
        }
        
        public static String[] GetMessageEnemy(int enemy_type) {
        	switch(enemy_type) {
        	case BOSS2:
        		return new String[] {"Này, kẻ kỳ lạ kia, nhìn ngươi giống như cây cỏ sống dậy. Rừng này\nngươi cai quản à? Đừng nói rằng ngươi định ngăn lão Tôn qua đây nhé!",
        				"Ngươi là kẻ gây náo loạn trời đất, lòng đầy kiêu ngạo, không biết\nkính sợ thiên nhiên. Ta là Mộc Vân Kiếm Linh, linh hồn của cánh rừng này.\nNơi đây là cội nguồn của sự sống, không dung thứ cho kẻ xấc láo phá hoại",
        				"Ha! Thiên cung còn chẳng giữ được lão Tôn này, ngươi chỉ là một cái\ncây biết đi mà cũng muốn làm khó ta sao? Hay ngươi muốn thử sức với cây\ngậy Như Ý này?",
        				"Sự kiêu ngạo của ngươi đã làm ô uế rừng sâu này. Thanh kiếm này là\nhiện thân của thiên nhiên, mỗi nhát kiếm sẽ là lời phán xét. Ngươi có sẵn\nsàng đối mặt chưa?",
        				"Tốt, tốt! Đã lâu rồi lão Tôn chưa gặp được đối thủ thú vị. Được, để\nxem cội nguồn của ngươi mạnh đến đâu!"};
        	case BOSS3:
        		return new String[] {"Một tên quái vật mặt đỏ, dám cản đường Đại Thánh ta! Mau xưng tên\nđể ta còn đưa ngươi về Tây Thiên làm quà!",
        				"Bật Mã Ôn,ngươi chỉ là một con khỉ vô dụng.Ta là Hắc Thủy Hành Giả,\nkẻ cai quản dòng nước sâu thẳm, nơi mà ánh sáng cũng không dám bén mảng",
        				"Nói nhiều vô ích! Có giỏi thì ra đây, lão Tôn ta đánh một gậy, xem\ndòng nước của ngươi chảy được đến đâu!",
        				"Sức mạnh của dòng nước là vô giá. Tôn Ngộ Không, đừng coi thường ta...\nNhững kẻ chống lại dòng nước của ta trước đây đều bị nhấn chìm\nbởi dòng nước này!",
        				"Để xem là nước chìm ta, hay gậy Như Ý đập vỡ đầu ngươi trước!"};
        	case TORO:
        		return new String[] {"Một con trâu to xác nữa cản đường lão Tôn ta à? Không biết ngươi\ntừ đâu bò ra vậy?",
        							"Kẻ ngạo mạn! Ta là Ngưu Ma Vương, thần thú của đại địa, người giữ cân\nbằng cho đất trời. Còn ngươi chỉ là con khỉ phá phách, dám xâm phạm\n lãnh thổ của ta!",
        				"Đất này của lão Tôn muốn đi thì đi, muốn đứng thì đứng. Đừng tưởng thân\n to xác thì lão Tôn sợ ngươi nhé! Ngươi có giỏi thì tới đây thử xem!",
        				"Ta là đất, ta là núi, không gì có thể phá vỡ được! Thạch Hầu,\nhôm nay ngươi sẽ bị chôn vùi dưới chân ta!",
        				"Chà, nặng lời thế làm gì! Lão Tôn ta chẳng sợ núi, lại càng\nkhông ngán đất! Lên đi, xem ai mới là kẻ bị đè bẹp!"};
        	case BOSS4:
        		return new String[] {"Hừm, nơi này oi bức quá! Ngươi là kẻ nào mà dám làm càn trong cõi\nnày? Chẳng lẽ muốn thử sức với Tề Thiên Đại Thánh ta sao?",
        				"Thật to gan, con khỉ kia ngươi là ai mà dám đến đấy quậy phá\nnơi ở của ta! Ta là Xích Diệm Ma Vương, thống lĩnh lửa ngục.\nĐịa ngục ba nghìn năm cháy dữ, nay ta bước ra để thiêu rụi mọi kẻ\ndám xâm phạm nơi này.",
        				"Bất hạnh cho nhà ngươi khi không biết đến ta. Xin tự giới thiệu ta\nlà Tề Thiên Đại Thánh, 500 năm trước đại náo thiên cung,Ngọc Hoàng\ncòn phải kinh sợ trước ta. Ngươi nghĩ mình đủ khả năng để cản bước\nđược ta sao?",
        				"Hahaha hoá ra là nhà ngươi. Nhưng mà câu chuyện đó là 500 năm trước\nrồi, bây giờ ngươi cũng chỉ là một con khỉ vô dụng thôi.Để ta cho\nngươi thành tro bụi dưới thanh Hoả Đồ Đao này!",
        				"Ngươi nghĩ chút lửa này làm khó được Tề Thiên Đại Thánh à? Cả trời\nđất còn không trói được lão Tôn ta, huống chi một ngọn lửa tàn lụi\nnhư ngươi",
        				"Lửa của ta không bao giờ tắt! Hãy xem Hỏa Thiên Cuồng Bạo thiêu\ncháy linh hồn ngươi!"};
        	case BOSSFINAL:
        		return new String[] {"Chúc mừng. chúc mừng nhà ngươi đã đặt chân được đến đây. Quả không\n hổ danh là Tề Thiên Đại Thánh!",
        				"Nhà ngươi quá khen rồi. Nhưng ngươi là ai mà dám to tiếng ở đây,\nchưa kể ngươi còn có ngoại hình rất giống ta nữa",
        				"Giống ư? Ta chính là hình bóng mà ngươi sợ hãi nhất. Kim Diện Hành Giả\n— kẻ phản chiếu ngươi trong bản thể hoàn hảo nhất",
        				"Ha! Hoàn hảo? Bản thể này của lão Tôn đâu có thứ nào thay thế được.\nBỏ mặt nạ xuống xem, ta đánh một gậy vỡ mặt nạ vàng của ngươi bây giờ!",
        				"Đây đâu phải mặt nạ của ta. Khả năng của ta là hấp thụ mọi tinh hoa\ntrời đất để hội tụ lại thành một sức mạnh vô song, không gì có thể đánh\nbại được",
        				"Vậy hoá ra ngươi đã học được kĩ năng của ta?",
        				"Đúng, ta không chỉ học được mà còn tu luyện thêm để nó trở nên mạnh\nmẽ và đáng sợ hơn rất nhiều so với nhà ngươi",
        				"Chà chà, nghe có vẻ đáng sợ đấy, nhưng những kẻ cản đường ta trước đó\nđều chịu chung số phận là chết! Mau tránh ra nếu không muốn có chung\nsố phận đó",
        				"Ngươi nghĩ chừng đó đủ doạ ta sao. Kim khắc đá, lửa tôi luyện, ta sẽ\nnghiền nát khỉ đá ngạo mạn như ngươi!",
        				"Cái thứ mặt nạ lạnh lẽo này chỉ dọa được trẻ con thôi!Đến đây, xem\nthử bản lĩnh của nhà ngươi đến đâu!",
        				"Ngươi sẽ tan thành bụi dưới cây gậy của ta. Gậy sắt không biết đau,\nvà ta cũng vậy"};
        		
        		
        	default:
        		return new String[] {"Khá lắm khá lắm, cuối cùng ngươi cũng đã đến được đây",
        				"Tôn Ngộ Không, người thật to gan, dám đến quậy phá nơi ở của ta,\n tội ngươi xứng đáng chết"
        				,"Hôm nay ta phải dạy cho ngươi một bài học"};
        	}
        }
    }



    public static class Environment {
        public static final int BIG_CLOUD_WIDTH_DEFAULT = 448;
        public static final int BIG_CLOUD_HEIGHT_DEFAULT = 101;
        public static final int SMALL_CLOUD_WIDTH_DEFAULT = 74;
        public static final int SMALL_CLOUD_HEIGHT_DEFAULT = 24;

        public static final int BIG_CLOUD_WIDTH = (int) (BIG_CLOUD_WIDTH_DEFAULT * Game.SCALE);
        public static final int BIG_CLOUD_HEIGHT = (int) (BIG_CLOUD_HEIGHT_DEFAULT * Game.SCALE);
        public static final int SMALL_CLOUD_WIDTH = (int) (SMALL_CLOUD_WIDTH_DEFAULT * Game.SCALE);
        public static final int SMALL_CLOUD_HEIGHT = (int) (SMALL_CLOUD_HEIGHT_DEFAULT * Game.SCALE);
    }

    public static class UI {
        public static class Buttons {
            public static final int B_WIDTH_DEFAULT = 140;
            public static final int B_HEIGHT_DEFAULT = 56;
            public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
            public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
        }

        public static class PauseButtons {
            public static final int SOUND_SIZE_DEFAULT = 42;
            public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * Game.SCALE);
        }

        public static class URMButtons {
            public static final int URM_DEFAULT_SIZE = 56;
            public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * Game.SCALE);

        }

        public static class VolumeButtons {
            public static final int VOLUME_DEFAULT_WIDTH = 28;
            public static final int VOLUME_DEFAULT_HEIGHT = 44;
            public static final int SLIDER_DEFAULT_WIDTH = 215;

            public static final int VOLUME_WIDTH = (int) (VOLUME_DEFAULT_WIDTH * Game.SCALE);
            public static final int VOLUME_HEIGHT = (int) (VOLUME_DEFAULT_HEIGHT * Game.SCALE);
            public static final int SLIDER_WIDTH = (int) (SLIDER_DEFAULT_WIDTH * Game.SCALE);
        }
    }

    public static class Directions {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class PlayerConstants {
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int JUMP = 2;
        public static final int FALLING = 3;
        public static final int ATTACK = 4;


        public static final int HIT = 5;
        public static final int DEAD = 6;
        public static final int ULTI = 7;
        public static final int THROW = 8;




        public static int GetSpriteAmount(int player_action) {
            switch (player_action) {
                
                
                case IDLE:
                    return 5;
                case RUNNING:
                  return 6;
                case JUMP:
                	return 3;
                case HIT:
                case THROW:
                  return 4;
                case ATTACK:
                    return 3;
                case ULTI:
                    return 8;
                case FALLING:
                	return 1;
                case DEAD:
                    return 7;
                default:
                    return 3;
            }
        }

        //Mana hao tốn
        public static int GetStamina(int player_action){
            switch(player_action){
                case JUMP:
                    return 1;
                case ATTACK:
                    return 3;
                case ULTI :
                    return 40;
                case THROW:
                    return 20;
                default: 
                    return 0;
            }
        }
        public static int GetPlayerDamage(int player_action){
            switch(player_action){
                case ATTACK:
                    return 10;
                case ULTI :
                    return 40   ;
                case THROW:
                    return 15;
                default:
                    return 0;
            }
        }



        public static int GetAniFromKey(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_SPACE:
                    return JUMP;
                
                case KeyEvent.VK_F:
                    return ATTACK;
                case KeyEvent.VK_G:
                    return ULTI;
                default: 
                    return IDLE;
            }
        }

        public static boolean NeedToCheckStamina(KeyEvent e){
            switch (e.getKeyCode()) {
                case KeyEvent.VK_SPACE:
                case KeyEvent.VK_A:
                case KeyEvent.VK_D:
                case KeyEvent.VK_W:
                case KeyEvent.VK_J:
                case KeyEvent.VK_K:
                case KeyEvent.VK_L:
                    return true;
                default: 
                    return false;
            }
        }
    }

}
