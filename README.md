# 백준 1260 - DFS와 BFS: 그래프 탐색의 두 가지 접근

[![문제 링크](https://img.shields.io/badge/Baekjoon-1260-blue)](https://www.acmicpc.net/problem/1260)
[![블로그 포스트](https://img.shields.io/badge/Blog-Read%20Full%20Story-green)](https://b-mokk.tistory.com/85)

> 단순히 "DFS는 깊이 우선, BFS는 너비 우선"이라는 개념을 넘어서, 실제 구현 시 마주치는 선택 지점들을 다룬다.

## 🎯 핵심 개념

### DFS (Depth-First Search)
- **자료구조**: Stack (재귀 호출 스택)
- **탐색 방식**: 깊이 우선
- **시간복잡도**: O(V+E)
- **공간복잡도**: O(V)
- **주요 용도**: 경로 존재 여부, 모든 경로 탐색, 백트래킹

### BFS (Breadth-First Search)
- **자료구조**: Queue
- **탐색 방식**: 너비 우선
- **시간복잡도**: O(V+E)
- **공간복잡도**: O(V)
- **주요 용도**: 최단 경로, 레벨별 처리

## 📂 프로젝트 구조

```
src/main/java/
├── solution/
│   └── Main.java                    # 최종 솔루션
├── impl/                            # 구현 방식별 비교
│   ├── AdjacencyList.java          # 인접 리스트 기반
│   ├── AdjacencyMatrix.java        # 인접 행렬 기반
│   ├── DfsRecursive.java           # 재귀 DFS
│   ├── DfsStack.java               # Stack DFS
│   └── BfsQueue.java               # Queue BFS
├── mistakes/                        # 공통 함정 예제
│   ├── WrongBidirectional.java     # 양방향 간선 실수
│   ├── MissingSort.java            # 정렬 누락
│   ├── BfsWrongVisitCheck.java    # 방문 체크 시점 실수
│   └── MissingVisitedReset.java   # visited 초기화 누락
└── util/                            # 유틸리티
    └── GraphConverter.java         # 인접 행렬 ↔ 리스트 변환
```

## 📚 코드 설명

### 최종 솔루션
- **[Main.java](src/main/java/solution/Main.java)**
  - 인접 리스트 + 재귀 DFS + Queue BFS
  - 양방향 간선, 정렬, 방문 체크 모두 반영
  - 백준 제출용 코드

### 구현 방식 비교

#### 1. 그래프 표현 방식

**인접 리스트** - [AdjacencyList.java](src/main/java/impl/AdjacencyList.java)
- 메모리: O(N+M)
- 간선 확인: O(degree)
- 전체 탐색: O(N+M)
- **이 문제에 적합** (희소 그래프: N=1000, M=10000)

**인접 행렬** - [AdjacencyMatrix.java](src/main/java/impl/AdjacencyMatrix.java)
- 메모리: O(N²)
- 간선 확인: O(1)
- 전체 탐색: O(N²)
- 밀집 그래프에 적합

#### 2. DFS 구현 방식

**재귀 DFS** - [DfsRecursive.java](src/main/java/impl/DfsRecursive.java)
- 간결하고 직관적
- 암묵적 Stack 사용
- 함수 호출 스택 = Stack 구조

**Stack DFS** - [DfsStack.java](src/main/java/impl/DfsStack.java)
- 명시적 Stack 사용
- 깊이 제한 없음
- 역순 순회로 재귀와 동일한 결과

#### 3. BFS 구현

**Queue BFS** - [BfsQueue.java](src/main/java/impl/BfsQueue.java)
- FIFO 구조로 레벨별 탐색
- **핵심**: offer 시점에 방문 체크 (중복 방지)

### 공통 함정 (Common Mistakes)

#### 1. 양방향 간선 누락 - [WrongBidirectional.java](src/main/java/mistakes/WrongBidirectional.java)
```java
// ❌ 잘못된 구현
graph[u].add(v);  // 한쪽만 추가

// ✅ 올바른 구현
graph[u].add(v);
graph[v].add(u);  // 양방향!
```

#### 2. 정렬 누락 - [MissingSort.java](src/main/java/mistakes/MissingSort.java)
```java
// ❌ 문제: 입력 순서대로 방문
// 입력: 1-3, 1-2, 1-4 → graph[1] = [3, 2, 4]
// 결과: 1 → 3 → 2 → 4 (틀림!)

// ✅ 해결: 정렬 필수
Collections.sort(graph[i]);
// 결과: 1 → 2 → 3 → 4 (정상)
```

#### 3. BFS 방문 체크 시점 - [BfsWrongVisitCheck.java](src/main/java/mistakes/BfsWrongVisitCheck.java)
```java
// ❌ 잘못된 구현: poll 시점 체크
int node = queue.poll();
if (visited[node]) continue;  // 중복 삽입 발생!

// ✅ 올바른 구현: offer 시점 체크
queue.offer(next);
visited[next] = true;  // 중복 방지
```

#### 4. visited 초기화 누락 - [MissingVisitedReset.java](src/main/java/mistakes/MissingVisitedReset.java)
```java
// ❌ 문제: DFS 후 visited가 모두 true
dfs(V);
bfs(V);  // BFS 실행 안 됨!

// ✅ 해결: 초기화 필수
dfs(V);
Arrays.fill(visited, false);
bfs(V);
```

### 유틸리티

**그래프 변환** - [GraphConverter.java](src/main/java/util/GraphConverter.java)
- 인접 행렬 → 인접 리스트 변환
- 인접 리스트 → 인접 행렬 변환
- 양방향 변환 검증 예제 포함

## 💡 핵심 학습 포인트

### 1. 그래프 표현 선택 기준

| 구분 | 인접 행렬 | 인접 리스트 |
|------|----------|------------|
| 메모리 | O(N²) | O(N+M) |
| 간선 확인 | O(1) | O(degree) |
| 전체 탐색 | O(N²) | O(N+M) |
| 적합한 경우 | 밀집 그래프 | 희소 그래프 |

**이 문제**: N=1000, M=10000 → **인접 리스트 선택**

### 2. 방문 체크 시점

| 알고리즘 | 체크 시점 | 이유 |
|---------|---------|------|
| DFS (재귀) | 진입 시 | 재귀 호출 전에 체크 |
| DFS (Stack) | pop 시 | 중복 방지 |
| BFS | **offer 시** | 중복 삽입 방지 |

**BFS에서 poll 시점 체크의 문제:**
```
그래프: 1-2, 1-3, 2-3
queue: [1]
1 poll → 2, 3 offer → queue: [2, 3]
2 poll → 3 offer → queue: [3, 3]  // 중복!
```

### 3. 구현 체크리스트

- ✅ 인접 리스트로 그래프 구성
- ✅ 양방향 간선 양쪽 추가 (`graph[u].add(v)`, `graph[v].add(u)`)
- ✅ 인접 리스트 정렬 (`Collections.sort`)
- ✅ DFS: 재귀 진입 시 방문 체크
- ✅ BFS: offer 시점 방문 체크
- ✅ visited 배열 초기화 (DFS 후 BFS 전)
- ✅ StringBuilder 사용 (성능)
- ✅ ArrayDeque 사용 (Queue 구현)

## 🚀 실행 방법

### 최종 솔루션 실행
```bash
# 백준 제출용
cd src/main/java
javac solution/Main.java
java solution.Main
```

### 각 구현 방식 테스트
```bash
# 인접 리스트
java impl.AdjacencyList

# 인접 행렬
java impl.AdjacencyMatrix

# 재귀 DFS
java impl.DfsRecursive

# Stack DFS
java impl.DfsStack

# Queue BFS
java impl.BfsQueue
```

### 공통 함정 예제 실행
```bash
# 양방향 간선 실수
java mistakes.WrongBidirectional

# 정렬 누락
java mistakes.MissingSort

# BFS 방문 체크 실수
java mistakes.BfsWrongVisitCheck

# visited 초기화 누락
java mistakes.MissingVisitedReset
```

### 그래프 변환 예제 실행
```bash
java util.GraphConverter
```

## 📊 비교표

### DFS vs BFS

| 구분 | DFS | BFS |
|------|-----|-----|
| 자료구조 | Stack (재귀) | Queue |
| 탐색 방식 | 깊이 우선 | 너비 우선 |
| 시간복잡도 | O(V+E) | O(V+E) |
| 공간복잡도 | O(V) | O(V) |
| 주요 용도 | 경로 존재, 모든 경로 | 최단 경로, 레벨별 처리 |

### 재귀 DFS vs Stack DFS

| 구분 | 재귀 | Stack |
|------|------|-------|
| 방문 체크 | 진입 시 | pop 시 |
| 코드 간결성 | 간결 | 복잡 |
| 스택 깊이 | 제한 있음 | 메모리까지 |
| 디버깅 | 어려움 | 쉬움 |

## 🔗 관련 링크

- [백준 1260번 문제](https://www.acmicpc.net/problem/1260)
- [블로그 포스트](https://b-mokk.tistory.com/85)

---

**Keywords**: `DFS`, `BFS`, `Graph Traversal`, `Adjacency List`, `Adjacency Matrix`, `Stack`, `Queue`, `백준`, `알고리즘`
