package solution;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 백준 1260 - DFS와 BFS
 * 
 * 최종 솔루션: 인접 리스트 + 재귀 DFS + Queue BFS
 * 
 * 핵심 구현 사항:
 * 1. 양방향 간선 처리 (graph[u].add(v), graph[v].add(u))
 * 2. 인접 리스트 정렬 (작은 번호부터 방문)
 * 3. DFS: 재귀 진입 시 방문 체크
 * 4. BFS: offer 시점 방문 체크 (중복 방지)
 * 5. visited 배열 초기화 (DFS 후 BFS 전)
 */
public class Main {
	static List<Integer>[] graph;
	static boolean[] visited;
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int N = Integer.parseInt(st.nextToken()); // 정점의 개수
		int M = Integer.parseInt(st.nextToken()); // 간선의 개수
		int V = Integer.parseInt(st.nextToken()); // 시작 정점

		// 인접 리스트 초기화
		@SuppressWarnings("unchecked")
		List<Integer>[] graphArray = new ArrayList[N + 1];
		graph = graphArray;
		for (int i = 1; i <= N; i++) {
			graph[i] = new ArrayList<>();
		}

		// 간선 입력 (양방향)
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			graph[u].add(v);
			graph[v].add(u); // 양방향 간선
		}

		// 인접 리스트 정렬 (작은 번호부터 방문)
		for (int i = 1; i <= N; i++) {
			Collections.sort(graph[i]);
		}

		// DFS
		visited = new boolean[N + 1];
		dfs(V);
		sb.append("\n");

		// visited 배열 초기화
		visited = new boolean[N + 1];

		// BFS
		bfs(V);

		System.out.println(sb);
	}

	/**
	 * 재귀를 사용한 DFS 구현
	 * - 재귀 진입 시 방문 체크
	 * - 인접한 정점 중 방문하지 않은 것만 재귀 호출
	 */
	private static void dfs(int node) {
		visited[node] = true;
		sb.append(node).append(" ");

		for (int next : graph[node]) {
			if (!visited[next]) {
				dfs(next);
			}
		}
	}

	/**
	 * Queue를 사용한 BFS 구현
	 * - offer 시점에 방문 체크 (중복 삽입 방지)
	 * - poll 시점에는 출력만 수행
	 */
	private static void bfs(int start) {
		Queue<Integer> queue = new ArrayDeque<>();
		queue.offer(start);
		visited[start] = true;

		while (!queue.isEmpty()) {
			int node = queue.poll();
			sb.append(node).append(" ");

			for (int next : graph[node]) {
				if (!visited[next]) {
					queue.offer(next);
					visited[next] = true; // offer 시점 체크
				}
			}
		}
	}
}
