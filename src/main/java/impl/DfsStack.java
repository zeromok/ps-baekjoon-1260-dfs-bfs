package impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Stack을 명시적으로 사용한 DFS 구현
 * 
 * 재귀 vs Stack 비교:
 * 
 * | 구분 | 재귀 | Stack |
 * |------|------|-------|
 * | 방문 체크 | 진입 시 | pop 시 |
 * | 코드 간결성 | 간결 | 복잡 |
 * | 스택 깊이 | 제한 있음 | 메모리까지 |
 * | 디버깅 | 어려움 | 쉬움 |
 * 
 * Stack 구현 시 주의사항:
 * - 역순으로 순회해야 재귀와 동일한 결과
 * - pop 시점에 방문 체크 (중복 방지)
 */
public class DfsStack {
	static List<Integer>[] graph;
	static boolean[] visited;
	static StringBuilder sb = new StringBuilder();
	static int N;

	public static void main(String[] args) {
		// 테스트 데이터
		N = 4;
		int V = 1;

		int[][] edges = {
			{1, 2},
			{1, 3},
			{1, 4},
			{2, 4},
			{3, 4}
		};

		// 그래프 구성
		@SuppressWarnings("unchecked")
		List<Integer>[] graphArray = new ArrayList[N + 1];
		graph = graphArray;
		for (int i = 1; i <= N; i++) {
			graph[i] = new ArrayList<>();
		}

		for (int[] edge : edges) {
			int u = edge[0];
			int v = edge[1];
			graph[u].add(v);
			graph[v].add(u);
		}

		// 정렬
		for (int i = 1; i <= N; i++) {
			Collections.sort(graph[i]);
		}

		// Stack DFS 실행
		visited = new boolean[N + 1];
		dfsStack(V);

		System.out.println(sb);
	}

	/**
	 * Stack을 사용한 DFS
	 * 
	 * 핵심:
	 * 1. 역순으로 순회 (마지막 원소부터 push) → 재귀와 동일한 순서
	 * 2. pop 시점에 방문 체크 (이미 방문했으면 continue)
	 * 
	 * 왜 역순인가?
	 * - Stack은 LIFO (Last In First Out)
	 * - 재귀는 첫 번째 인접 노드를 먼저 방문
	 * - 따라서 마지막 원소를 먼저 push해야 첫 번째 원소가 먼저 pop됨
	 */
	private static void dfsStack(int start) {
		Stack<Integer> stack = new Stack<>();
		stack.push(start);

		while (!stack.isEmpty()) {
			int node = stack.pop();

			// pop 시점에 방문 체크 (중복 방지)
			if (visited[node]) {
				continue;
			}

			visited[node] = true;
			sb.append(node).append(" ");

			// 역순으로 순회하여 재귀와 동일한 결과
			for (int i = graph[node].size() - 1; i >= 0; i--) {
				int next = graph[node].get(i);
				if (!visited[next]) {
					stack.push(next);
				}
			}
		}
	}
}
