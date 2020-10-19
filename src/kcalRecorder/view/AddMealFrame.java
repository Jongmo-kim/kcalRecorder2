package kcalRecorder.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import kcalRecorder.model.vo.Food;
//TODO 엔터키를 누르면 field에 입력된 값이 자동으로 저장되지만 focus가 3번째 위치에 되어있다
// 이를 1번째로 바꾸어보자.

public class AddMealFrame extends JFrame {
	Stack<Food> addedFood;
	ArrayList<Food> FoodList;
	nameInputPanel nameInputPanel;
	kcalPer100GramInputPanel kcalPer100GramInputPanel;
	sizeInputPanel sizeInputPanel;
	addedFoodWithScrollBar addedFoodWithScrollBar;
	addedFoodMainPanel addedFoodMainPanel;
	addMealFrameAddButton addMealFrameAddButton;
	addMealFrameConfirmButton addMealFrameConfirmButton;
	public ArrayList<Food> getFoodList() {
		return FoodList;
	}

	public addMealFrameConfirmButton getAddMealFrameConfirmButton() {
		return addMealFrameConfirmButton;
	}


	public AddMealFrame() {
		FoodList = new ArrayList<Food>();
		addedFood = new Stack<Food>();
		setDefaultOptions();
		setGridBagConstraintsLayout();
	}

	private void setGridBagConstraintsLayout() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		addPanel ap = new addPanel();
		add(ap, gbc);

		addedFoodMainPanel = new addedFoodMainPanel();
		// addedPanel.setPreferredSize(new Dimension(200,150));
		gbc.weightx = 0.6;
		gbc.weighty = 1;
		add(addedFoodMainPanel, gbc);
	}

	private void setDefaultOptions() {
		setSize(700, 400);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setLayout(new GridLayout(1, 2));
	}

	public class addPanel extends JPanel {
		public addPanel() {
			setBorder(BorderFactory.createLineBorder(Color.black));
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			add(new JLabel("먹을것 추가하기"));
			add(nameInputPanel = new nameInputPanel());
			add(kcalPer100GramInputPanel = new kcalPer100GramInputPanel());
			add(sizeInputPanel = new sizeInputPanel());
			JPanel tempPanel = new JPanel();
			tempPanel.add(addMealFrameAddButton = new addMealFrameAddButton("추가"));
			
			tempPanel.add(addMealFrameConfirmButton = new addMealFrameConfirmButton("완료"));
			add(tempPanel);
//			add(new confirmButton("완료"));
//			add(new addMealFrameAddButton("추가"));

		}
	}

	public class addMealFrameAddButton extends JButton {
		addMealFrameAddButton(String s) {
			super(s);
			addActionListener(addMealFrameAddButtonActionListener());
		}

		public ActionListener addMealFrameAddButtonActionListener() {
			ActionListener e = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					addFoodFromTextField();
					addedFoodWithScrollBar.updateFood();
					// TODO 이제 update된 addedFoodWithScrollbar를 뵈게끔만하면댐
				}

			};
			return e;
		}
	}

	public void addFoodFromTextField() {
		String name = nameInputPanel.getText();
		String kcalPerGram = kcalPer100GramInputPanel.getText();
		String size = sizeInputPanel.getText();
		int kcal = Integer.parseInt(kcalPerGram);
		double size_ = Double.parseDouble(size);
		Food f = new Food(kcal, size_, name);
		addedFood.add(f);

	}

	public class addedFoodPanel extends JPanel {
		public addedFoodPanel(Food f) {
			// setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
			add(new addedFoodNamePanel(f.getName()));
			add(new addedFoodSizePanel(f.getSize()));
			add(new addedFoodTotalKcalPanel(f.getTotalKcal()));
		}
	}

	public class addedFoodNamePanel extends JPanel {
		public addedFoodNamePanel(String name) {
			JLabel indicator = new JLabel("음식 이름 :");
			JLabel name_ = new JLabel(name);
			add(indicator);
			add(name_);
		}
	}

	public class addedFoodSizePanel extends JPanel {
		public addedFoodSizePanel(double size) {
			JLabel indicator = new JLabel("몇 인분 :");
			JLabel size_ = new JLabel(Double.toString(size));
			add(indicator);
			add(size_);
		}
	}

	public class addedFoodTotalKcalPanel extends JPanel {
		public addedFoodTotalKcalPanel(int totalKcal) {
			JLabel indicator = new JLabel("음식 칼로리 :");
			JLabel totalKcal_ = new JLabel(Integer.toString(totalKcal));
			add(indicator);
			add(totalKcal_);
		}
	}

	public class addedFoodWithScrollBar extends JPanel {
		public addedFoodWithScrollBar() {
			super();
			setAutoscrolls(true);
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		}

		public void updateFood() {
			while (!addedFood.isEmpty()) {
				Food dd = addedFood.pop();
				FoodList.add(dd);
				addedFoodPanel e = new addedFoodPanel(dd);
				add(e);
			}
			super.updateUI();

		}
	}

	public class addMealFrameConfirmButton extends JButton {
		addMealFrameConfirmButton(String s) {
			super(s);
			addActionListener(confirmButtonActionListener());
		}

		public ActionListener confirmButtonActionListener() {
			ActionListener e = new ActionListener() {
				public void actionPerformed(ActionEvent e) {

				}

			};
			return null;
		}
	}

	public class nameInputPanel extends JPanel {
		JLabel nameInputLabel;
		JTextField nameInputField;

		nameInputPanel() {
			add(nameInputLabel = new JLabel("* Name\t"));
			add(nameInputField = new JTextField(10));
		}

		public JLabel getLabel() {
			return nameInputLabel;
		}

		public String getText() {
			String s = nameInputField.getText();
			nameInputField.setText("");
			return s;
		}
	}

	public class kcalPer100GramInputPanel extends JPanel {
		JLabel kcalPer100GramLabel;
		JTextField kcalPer100GramField;

		kcalPer100GramInputPanel() {
			add(kcalPer100GramLabel = new JLabel("* Kcal Per 100 Gram\t"));
			add(kcalPer100GramField = new JTextField(10));
		}

		public JLabel getLabel() {
			return kcalPer100GramLabel;
		}

		public String getText() {
			String s = kcalPer100GramField.getText();
			kcalPer100GramField.setText("");
			return s;
		}

	}

	public class sizeInputPanel extends JPanel {
		JLabel sizeLabel;
		JTextField sizeField;

		sizeInputPanel() {
			add(sizeLabel = new JLabel("* Size \t"));
			add(sizeField = new JTextField(10));
			sizeField.addActionListener(addButtonActionListener());
		}

		public JLabel getSizeLabel() {
			return sizeLabel;
		}

		public String getText() {
			String s = sizeField.getText();
			sizeField.setText("");
			return s;
		}

		public ActionListener addButtonActionListener() {
			ActionListener e = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					addFoodFromTextField();
					addedFoodWithScrollBar.updateFood();

				}

			};
			return e;
		}
	}

	public class addedFoodMainPanel extends JPanel {
		public addedFoodMainPanel() {
			setBorder(BorderFactory.createLineBorder(Color.black));
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			add(new JLabel("* 먹은 음식들"));
			add(addedFoodWithScrollBar = new addedFoodWithScrollBar());
		}
	}
	public void setInvisible() {
		setVisible(false);
		setEnabled(false);
	}
	public void setVisible() {
		setVisible(true);
		setEnabled(true);
	}
}
