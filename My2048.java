import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class My2048 {
	int[][] arr;
	Set<Point_2048> zeros;// 如果有一个长期维护的o的哈希集合，不用每次新建了就
	boolean fillflag;

	public My2048(int length) {
		fillflag = true;
		arr = new int[length][length];
		zeros = new HashSet<Point_2048>();// 没想到用什么手段动态维护空位
		fill(arr);
		fill(arr);
	}

	// 等概率填充字符
	public static void fill(int[][] arr) {
		List<Point_2048> pointContainer = new ArrayList<Point_2048>();
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length; j++) {
				if (arr[i][j] == 0) {
					pointContainer.add(new Point_2048(i, j));
				}
			}
		}

		if (pointContainer.size() > 0) {
			Point_2048 temp = pointContainer.remove((int) (Math.random() * pointContainer.size()));
			arr[temp.x][temp.y] = Math.random() > 0.1 ? 2 : 4;// 2/4的概率为9：1
		}
	}

	// 移动面板,返回Boolean常量以确定是否填充
	// 维护太过繁琐了，一个方法不应该超过50行，而且还有大量类似的代码，极容易犯错。
	// 下次注意不要偷懒，1.引进junit进行单元测试2.代码复用
	// 复用的方法(我能想到的)：1.对于移动游标的动作和合并两个单元格的动作，将其抽象为函数，从而达到代码复用。
	// 2.写一个方向的函数，对于四个方向的不同情况通过旋转数组达到目的
	public static boolean adjust(int[][] arr, int direction) {
		boolean flag = false;
		if (direction == 1) {
			for (int i = 0; i < arr.length; i++) {
				int j = 0;
				int cursor = 1;
				while (cursor < arr.length && j < arr.length) {
					if (arr[i][cursor] != 0) {
						if (arr[i][j] == 0) {
							arr[i][j] = arr[i][cursor];
							arr[i][cursor] = 0;
							flag = true;
						} else {
							if (arr[i][j] == arr[i][cursor]) {
								arr[i][j] = 2 * arr[i][j];
								arr[i][cursor] = 0;
								flag = true;
							} else {
								if (cursor - j == 1) {
									j++;
								} else {
									arr[i][++j] = arr[i][cursor];
									arr[i][cursor] = 0;
									flag = true;
								}
							}
						}
					}
					cursor++;
				}
			}
		} else if (direction == 2) {
			for (int i = 0; i < arr.length; i++) {
				int j = arr.length - 1;
				int cursor = arr.length - 2;
				while (cursor >= 0 && j >= 0) {
					if (arr[i][cursor] == 0) {

					} else {
						if (arr[i][j] == 0) {
							arr[i][j] = arr[i][cursor];
							arr[i][cursor] = 0;
							flag = true;
						} else {
							if (arr[i][j] == arr[i][cursor]) {
								arr[i][j] = 2 * arr[i][j];
								flag = true;
								arr[i][cursor] = 0;
							} else {
								if (j - cursor == 1) {
									j--;
								} else {
									arr[i][--j] = arr[i][cursor];
									arr[i][cursor] = 0;
									flag = true;
								}
							}
						}
					}
					cursor--;
				}
			}
		} else if (direction == 3) {
			for (int j = 0; j < arr.length; j++) {
				int i = 0;
				int cursor = 1;
				while (cursor < arr.length && i < arr.length) {
					if (arr[cursor][j] == 0) {

					} else {
						if (arr[i][j] == 0) {
							arr[i][j] = arr[cursor][j];
							arr[cursor][j] = 0;
							flag = true;
						} else {
							if (arr[i][j] == arr[cursor][j]) {
								arr[i][j] = 2 * arr[i][j];
								flag = true;
								arr[cursor][j] = 0;
							} else {
								if (cursor - i == 1) {
									i++;
								} else {
									arr[++i][j] = arr[cursor][j];
									arr[cursor][j] = 0;
									flag = true;
								}
							}
						}
					}
					cursor++;
				}
			}
		} else if (direction == 4) {
			for (int j = 0; j < arr.length; j++) {
				int i = arr.length - 1;
				int cursor = arr.length - 2;
				while (cursor >= 0 && i >= 0) {
					if (arr[cursor][j] == 0) {

					} else {
						if (arr[i][j] == 0) {
							arr[i][j] = arr[cursor][j];
							arr[cursor][j] = 0;
							flag = true;
						} else {
							if (arr[i][j] == arr[cursor][j]) {
								arr[i][j] = 2 * arr[i][j];
								flag = true;
								arr[cursor][j] = 0;
							} else {
								if (i - cursor == 1) {
									i--;
								} else {
									arr[--i][j] = arr[cursor][j];
									arr[cursor][j] = 0;
									flag = true;
								}
							}
						}
					}
					cursor--;
				}
			}
		}
		return flag;
	}

	// 判断数组是否还有空位
	public static boolean hasZero(int[][] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length; j++) {
				if (arr[i][j] == 0) {
					return true;
				}
			}
		}
		return false;
	}

	// 主函数
	public static void main(String[] args) {
		My2048 begin = new My2048(4);
		print(begin.arr);
		System.out.println("输入1，2，3，4 :");
		Scanner in = new Scanner(System.in);
		while (in.hasNext()) {
			int direction = in.nextInt();
			if (adjust(begin.arr, direction)) {
				fill(begin.arr);
				print(begin.arr);
				System.out.println("输入1，2，3，4 :");
			} else {
				if (!hasZero(begin.arr)) {
					System.out.println("fail");
					break;
				}
			}
		}

	}

	// 打印数组
	public static void print(int[][] arr) {
		for (int[] i : arr) {
			for (int j : i) {
				System.out.print(String.format("%4d", j));// 格式化输出，这样才能看啊
				// System.out.print(j);
			}
			System.out.println();
		}
	}

	// 选择数组，还没用到
	// public static void rotate_matrix(int[][] arr) {
	// for (int i = 0; i < arr.length; i++) {
	// for (int j = 0; j < arr.length / 2; j++) {
	// int temp = arr[i][j];
	// arr[i][j] = arr[i][arr.length - 1 - j];
	// arr[i][arr.length - 1 - j] = temp;
	// }
	// }
	//
	// for (int i = 0; i < arr.length; i++) {
	// for (int j = i + 1; j < arr.length; j++) {
	// int temp = arr[i][j];
	// arr[i][j] = arr[j][i];
	// arr[j][i] = temp;
	// }
	// }
	// }

	// 没用上，判断两个数组是否相等
	// public static boolean judgeequal(int[][] arr1, int[][] arr2) {
	// for (int i = 0; i < arr1.length; i++) {
	// for (int j = 0; j < arr1.length; j++) {
	// if (arr1[i][j] != arr2[i][j]) {
	// return false;
	// }
	// }
	// }
	// return true;
	// }

}
