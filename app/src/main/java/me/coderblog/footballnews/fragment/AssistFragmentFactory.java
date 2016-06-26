package me.coderblog.footballnews.fragment;

import android.support.v4.app.Fragment;

import java.util.HashMap;
import java.util.Map;

/**
 * 助攻榜Frament工厂
 */
public class AssistFragmentFactory {
    //Map用来存储生成的Fragment对象，以达到复用Fragment
    private static Map<Integer, Fragment> fragmentMap = new HashMap<Integer, Fragment>();

    public static Fragment createFragment(int position) {

        //首先在Map查找有无对应的Fragment
        Fragment fragment = fragmentMap.get(position);

        if (fragment == null) {

            //如果Map中无对应的Fragment，则重新创建Fragment对象
            switch (position) {
                case 0:
                    fragment = new ChinaAssistFragment();
                    break;
                case 1:
                    fragment = new EnglandAssistFragment();
                    break;
                case 2:
                    fragment = new SpainAssistFragment();
                    break;
                case 3:
                    fragment = new GermanyAssistFragment();
                    break;
                case 4:
                    fragment = new ItalyAssistFragment();
                    break;
            }

            //将生成的Fragment对象存入Map中以便复用
            fragmentMap.put(position, fragment);
        }

        //返回Fragment
        return fragment;
    }
}
