package com.alibaba.alink.operator.common.evaluation;

import org.apache.flink.ml.api.misc.param.Params;
import org.apache.flink.ml.api.misc.param.WithParams;
import org.apache.flink.types.Row;

import java.io.Serializable;

/**
 * Base metrics for classification and regression evaluation.
 * <p>
 * All the evaluation metrics are calculated locally and merged through reduce function. Finally, they are saved as
 * params through toMetrics function.
 */
public abstract class BaseMetrics<M extends BaseMetrics<M>>
    implements WithParams<M>, Cloneable, Serializable {
    protected Params params;

    /**
     * Extract the params from a serialized row.
     *
     * @param row The Row generated by the serialize() function.
     */
    public BaseMetrics(Row row) {
        params = Params.fromJson(row.getField(0).toString());
    }

    public BaseMetrics(Params params) {
        this.params = params;
    }

    @Override
    public Params getParams() {
        if (null == this.params) {
            this.params = new Params();
        }
        return this.params;
    }

    @Override
    public M clone() throws CloneNotSupportedException {
        BaseMetrics result = (BaseMetrics)super.clone();
        result.params = this.params.clone();
        return (M)result;
    }

    /**
     * Serialize all the params into string.
     */
    public Row serialize() {
        return Row.of(this.params.toJson());
    }
}
