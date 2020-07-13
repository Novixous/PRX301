/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice.utils;

import java.util.List;

/**
 *
 * @author Novixous
 */
public class StatisticUtils {

    public static int median(int l, int r) {
        int n = r - l + 1;
        n = (n + 1) / 2 - 1;
        return n + l;
    }

    public static <T> T findQuartile(int quartile, List<T> list) throws Exception {
        int mix_index = median(0, list.size() - 1);
        switch (quartile) {
            case 1:
                return list.get(median(0, mix_index));
            case 2:
                return list.get(mix_index);
            case 3:
                return list.get(mix_index + median(mix_index + 1, list.size() - 1));
            default:
                throw new Exception("Quartile from 1 to 3 only!");

        }
    }
}
