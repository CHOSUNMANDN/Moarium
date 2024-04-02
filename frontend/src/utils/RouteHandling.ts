import { AppRouterInstance } from 'next/dist/shared/lib/app-router-context';
import { RouteUrl } from './constans/routeEnum';

/**
 * 메인페이지로 redirect을 해준다.
 * @param router
 */
export function replaceRouterMain(router: AppRouterInstance): void {
  router.replace(RouteUrl.ROUTE_MAIN);
}

/**
 * 초기 페이지로 redirect을 해준다.
 * @param router
 */
export function replaceRouterInitialize(router: AppRouterInstance): void {
  router.replace(RouteUrl.ROUTE_INITIALIZE);
}

/**
 * 이메일 찾기 페이지로 redirect을 한다.
 * @param router
 */
export function replaceRouterFindEmail(router: AppRouterInstance): void {
  router.replace(RouteUrl.ROUTE_FIND_EMAIL);
}

/**
 * 이메일 찾기 페이지로 redirect을 한다.
 * @param router
 */
export function replaceRouterFindPassword(router: AppRouterInstance): void {
  router.replace(RouteUrl.ROUTE_FIND_PASSWORD);
}

/**
 * 각각의 게시물 페이지로 replace을 한다.
 * @param router
 */
export function replaceRouterEachSuggestion(router: AppRouterInstance, id: number): void {
  router.replace(RouteUrl.ROUTE_EACH_SUGGESTION + id);
}

/**
 * 비밀번호 변경 페이지로 replace을 한다.
 * @param router
 */
export function replaceRouterPassword(router: AppRouterInstance): void {
  router.replace(RouteUrl.ROUTE_UPDATE_PASSWORD);
}

/**
 * 지원서 작성 페이지로 replace을 한다.
 * @param router
 */
export function replaceRouterApplyClub(router: AppRouterInstance, id: undefined | string): void {
  router.replace(RouteUrl.ROUTE_APPLY_CLUB + id);
}

/**
 * 초기 페이지 이동
 * @param router
 */
export function replaceRouterFirstLogin(router: AppRouterInstance): void {
  router.replace(RouteUrl.ROUTE_APPLY_CLUB);
}
