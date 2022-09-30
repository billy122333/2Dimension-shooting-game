package Final_Project;

public class Enemy {
	private int HP, DropMoney;
	private int attack_point;
	private String name;
	
	private int _HP, _DropMoney;
	private String _name;



	Enemy(String name , int HP , int drop) {
		_name = name;
		_HP = HP;
		_DropMoney = drop;
		this.reset();
	}
	
	public void reset() {
		name = _name;
		HP = _HP;
		DropMoney = _DropMoney;
	}

	String getName() {
		return name;
	}

	int getDropMoney() {
		return DropMoney;
	}

	int getatk_pt() {
		return attack_point;
	}

	int getHP() {
		return HP;
	}

	void setatk_pt(int AP) {
		attack_point = AP;
	}

	void setHP(int HP) {
		this.HP = HP;
	}

	void setName(String name) {
		this.name = name;
	}

	void setdropMoney(int drpmoney) {
		DropMoney = drpmoney;
	}
}
