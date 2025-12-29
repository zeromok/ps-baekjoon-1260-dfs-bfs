package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/// 인접 행렬 ↔ 인접 리스트 변환 유틸리티
/// 
/// 블로그 내용:
/// "인접 행렬에서 인접 리스트로 변환할 수 있을까?
///  반대로 리스트에서 행렬로도 가능할까?
///  가능하다."
/// 
/// 변환 방법:
/// 
/// **행렬 → 리스트:**
/// ```
/// for (int i = 1; i <= N; i++) {
///     for (int j = 1; j <= N; j++) {
///         if (matrix[i][j] == 1) {
///             list[i].add(j);
///         }
///     }
/// }
/// ```
/// 
/// **리스트 → 행렬:**
/// ```
/// for (int i = 1; i <= N; i++) {
///     for (int next : list[i]) {
///         matrix[i][next] = 1;
///     }
/// }
/// ```
public class GraphConverter {
	/// 인접 행렬 → 인접 리스트 변환
	/// 
	/// 방법:
	/// - 행렬을 순회하며 1인 위치를 리스트에 추가
	/// 
	/// @param matrix 인접 행렬 (N+1 x N+1, 1-indexed)
	/// @param N 정점의 개수
	/// @return 인접 리스트 배열
	@SuppressWarnings("unchecked")
	public static List<Integer>[] matrixToList(int[][] matrix, int N) {
		List<Integer>[] list = new ArrayList[N + 1];
		for (int i = 1; i <= N; i++) {
			list[i] = new ArrayList<>();
		}

		// 행렬 순회하며 1인 위치를 리스트에 추가
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				if (matrix[i][j] == 1) {
					list[i].add(j);
				}
			}
		}

		// 정렬 (선택사항, 문제 조건에 따라)
		for (int i = 1; i <= N; i++) {
			Collections.sort(list[i]);
		}

		return list;
	}

	/// 인접 리스트 → 인접 행렬 변환
	/// 
	/// 방법:
	/// - 리스트의 원소를 행렬에 1로 표시
	/// 
	/// @param list 인접 리스트 배열 (1-indexed)
	/// @param N 정점의 개수
	/// @return 인접 행렬 (N+1 x N+1, 1-indexed)
	public static int[][] listToMatrix(List<Integer>[] list, int N) {
		int[][] matrix = new int[N + 1][N + 1];

		// 리스트의 원소를 행렬에 1로 표시
		for (int i = 1; i <= N; i++) {
			for (int next : list[i]) {
				matrix[i][next] = 1;
			}
		}

		return matrix;
	}

	/// 변환 예제 및 테스트
	/// 
	/// 그래프 구조:
	/// ```
	///     1
	///    /|\
	///   2 3 4
	///   | | |
	///   | 4 |
	///   |/ \|
	///   4   4
	/// ```
	/// 
	/// 간선: 1-2, 1-3, 1-4, 2-4, 3-4
	public static void main(String[] args) {
		int N = 4;

		System.out.println("=== 그래프 구조 ===");
		System.out.println("    1");
		System.out.println("   /|\\");
		System.out.println("  2 3 4");
		System.out.println("  | | |");
		System.out.println("  | 4 |");
		System.out.println("  |/ \\|");
		System.out.println("  4   4");
		System.out.println("\n간선: 1-2, 1-3, 1-4, 2-4, 3-4\n");

		// 예제 1: 인접 행렬로 시작
		System.out.println("=== 예제 1: 인접 행렬 → 인접 리스트 ===");
		int[][] matrix = new int[N + 1][N + 1];
		matrix[1][2] = 1;
		matrix[2][1] = 1;
		matrix[1][3] = 1;
		matrix[3][1] = 1;
		matrix[1][4] = 1;
		matrix[4][1] = 1;
		matrix[2][4] = 1;
		matrix[4][2] = 1;
		matrix[3][4] = 1;
		matrix[4][3] = 1;

		System.out.println("\n인접 행렬:");
		System.out.println("   1 2 3 4");
		for (int i = 1; i <= N; i++) {
			System.out.print(i + " ");
			for (int j = 1; j <= N; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}

		List<Integer>[] list = matrixToList(matrix, N);
		System.out.println("\n변환된 인접 리스트:");
		for (int i = 1; i <= N; i++) {
			System.out.println("정점 " + i + ": " + list[i]);
		}

		// 예제 2: 인접 리스트로 시작
		System.out.println("\n=== 예제 2: 인접 리스트 → 인접 행렬 ===");
		@SuppressWarnings("unchecked")
		List<Integer>[] originalList = new ArrayList[N + 1];
		for (int i = 1; i <= N; i++) {
			originalList[i] = new ArrayList<>();
		}
		originalList[1].add(2);
		originalList[1].add(3);
		originalList[1].add(4);
		originalList[2].add(1);
		originalList[2].add(4);
		originalList[3].add(1);
		originalList[3].add(4);
		originalList[4].add(1);
		originalList[4].add(2);
		originalList[4].add(3);

		// 정렬
		for (int i = 1; i <= N; i++) {
			Collections.sort(originalList[i]);
		}

		System.out.println("\n인접 리스트:");
		for (int i = 1; i <= N; i++) {
			System.out.println("정점 " + i + ": " + originalList[i]);
		}

		int[][] convertedMatrix = listToMatrix(originalList, N);
		System.out.println("\n변환된 인접 행렬:");
		System.out.println("   1 2 3 4");
		for (int i = 1; i <= N; i++) {
			System.out.print(i + " ");
			for (int j = 1; j <= N; j++) {
				System.out.print(convertedMatrix[i][j] + " ");
			}
			System.out.println();
		}

		// 예제 3: 양방향 변환 검증
		System.out.println("\n=== 예제 3: 양방향 변환 검증 ===");
		int[][] roundTripMatrix = listToMatrix(list, N);
		boolean isEqual = true;
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				if (matrix[i][j] != roundTripMatrix[i][j]) {
					isEqual = false;
					break;
				}
			}
		}
		System.out.println("행렬 → 리스트 → 행렬 변환 검증: " + (isEqual ? "✅ 성공" : "❌ 실패"));

		List<Integer>[] roundTripList = matrixToList(convertedMatrix, N);
		boolean listEqual = true;
		for (int i = 1; i <= N; i++) {
			if (!originalList[i].equals(roundTripList[i])) {
				listEqual = false;
				break;
			}
		}
		System.out.println("리스트 → 행렬 → 리스트 변환 검증: " + (listEqual ? "✅ 성공" : "❌ 실패"));
	}
}

