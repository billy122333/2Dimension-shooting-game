package Final_Project;

public class Hero {
	public int HP;
	public int attack_point;
	public int Money;
	public String Name;
	
	Hero(String name) {
		Name = name;
	}

	String getName() {
		return Name;
	}

	int getatk_pt() {
		return attack_point;
	}

	int getHP() {
		return HP;
	}
	int getMoney() {
		return Money;
	}

	void setatk_pt(int AP) {
		attack_point = AP;
	}

	void setHP(int HP) {
		this.HP = HP;
	}
	void setName(String name) {
		Name =name;
	}
	void setMoney (int money) {
		Money = money;
	}

}
